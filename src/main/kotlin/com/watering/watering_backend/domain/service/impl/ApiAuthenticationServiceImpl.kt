package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.watering.watering_backend.domain.constant.Authority
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.constant.TokenType
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.isExpired
import com.watering.watering_backend.domain.exception.ApplicationException
import com.watering.watering_backend.domain.exception.RefreshTokenExpiredException
import com.watering.watering_backend.domain.exception.ResourceCreateFailedException
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.UserRegistrationFailedException
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.ApiAuthenticationService
import com.watering.watering_backend.domain.service.dto.authentication.ApiAuthenticateResult
import com.watering.watering_backend.domain.service.dto.authentication.ApiRegisterUserResult
import com.watering.watering_backend.domain.service.dto.authentication.RefreshAccessTokenResult
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.domain.service.shared.RefreshTokenSharedService
import com.watering.watering_backend.lib.extension.getErrorDescription
import com.watering.watering_backend.lib.extension.getOrThrow
import com.watering.watering_backend.lib.get
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ApiAuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository,
    private val authenticationManager: AuthenticationManager,
    private val accessTokenSharedService: AccessTokenSharedService,
    private val refreshTokenSharedService: RefreshTokenSharedService,
    private val passwordEncoder: PasswordEncoder
): ApiAuthenticationService {
    override fun authenticate(username: String, password: String): ApiAuthenticateResult = transaction {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().let { it.authentication = authentication }

        val userDetails: UserDetails = authentication.principal as UserDetails

        val userEntity: UserEntity = userRepository.getByUsername(userDetails.username)
                                                   .toEither {
                                                       ResourceNotFoundException(
                                                           httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                                                           error = Error.UNKNOWN_ERROR,
                                                           errorDescription = "User with username [${userDetails.username}] is not found in database.",
                                                           errorMessage = ApplicationException.UNKNOWN_MESSAGE
                                                       )
                                                   }
                                                   .getOrThrow()

        val (accessToken: AccessTokenEntity) = accessTokenSharedService.createAccessToken(userDetails)

        val refreshToken: RefreshTokenEntity = if (refreshTokenSharedService.existsByUserId(userEntity.id)) {
            refreshTokenSharedService.exchangeRefreshToken(userEntity.id)
        }
        else {
            refreshTokenSharedService.registerRefreshToken(userDetails)
        }

        val authorities: List<String> = userDetails.authorities.map { it.authority }

        ApiAuthenticateResult(
            userEntity,
            accessToken,
            refreshToken,
            authorities
        )
    }

    override fun registerUser(username: String, password: String, authorities: List<String>): ApiRegisterUserResult = transaction {
        if (userRepository.existsByUsername(username)) {
            throw UserRegistrationFailedException(
                errorDescription = "the user with username [${username}] already exists.",
                errorMessage = "the user with username [${username}] already exists. please choice another username."
            )
        }

        val authorityEntities: List<AuthorityEntity> = authorityRepository.getAuthoritiesByNameList(authorities).getOrHandle {
            throw UserRegistrationFailedException(
                errorDescription = it.getErrorDescription(),
                errorMessage = "Failed to create the user with authority [${authorities.joinToString(", ")}]. Please choice authorities from [${Authority.values().joinToString(", ")}]"
            )
        }

        val createdUser: UserEntity = userRepository.create(
            username,
            passwordEncoder.encode(password),
            authorityEntities
        ).getOrHandle {
            throw ResourceCreateFailedException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                error = Error.UNKNOWN_ERROR,
                errorDescription = it.getErrorDescription(),
                errorMessage = ApplicationException.UNKNOWN_MESSAGE
            )
        }

        ApiRegisterUserResult(
            createdUser,
            authorityEntities
        )
    }

    //TODO: refresh_token の値からユーザーを特定してそのユーザーのアクセストークンを再生成してるけど、良いのだろうか（ユーザーIDやアクセストークンもリクエストパラメーターにいれるべき？ でもそれじゃ Bearer じゃなくなる気がする）
    override fun refreshAccessToken(tokenUUID: UUID): RefreshAccessTokenResult = transaction {
        val refreshToken: RefreshTokenEntity = refreshTokenSharedService.getByTokenUUID(tokenUUID).getOrElse {
            throw ResourceNotFoundException(
                httpStatus = HttpStatus.BAD_REQUEST,
                error = Error.INVALID_REFRESH_TOKEN,
                errorDescription = "Refresh token [${tokenUUID}] is not found in database.",
                errorMessage = "the refresh token [${tokenUUID}] is invalid."
            )
        }

        if (refreshToken.isExpired()) {
            throw RefreshTokenExpiredException(refreshToken)
        }

        val user: UserEntity = userRepository.getById(refreshToken.userId).get()

        val (newAccessToken: AccessTokenEntity) = accessTokenSharedService.createAccessToken(user)
        val newRefreshToken: RefreshTokenEntity = refreshTokenSharedService.exchangeRefreshToken(refreshToken)

        RefreshAccessTokenResult(
            newAccessToken,
            newRefreshToken,
            TokenType.BEARER
        )
    }
}
