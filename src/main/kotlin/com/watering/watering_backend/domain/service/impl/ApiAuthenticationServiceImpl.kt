package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.constant.TokenType
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.isExpired
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.domain.exception.application.RefreshTokenExpiredException
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.ApiAuthenticationService
import com.watering.watering_backend.domain.service.dto.authentication.ApiAuthenticateResult
import com.watering.watering_backend.domain.service.dto.authentication.RefreshAccessTokenResult
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.domain.service.shared.RefreshTokenSharedService
import com.watering.watering_backend.lib.extension.getOrThrow
import com.watering.watering_backend.lib.extension.get
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
    private val authenticationManager: AuthenticationManager,
    private val accessTokenSharedService: AccessTokenSharedService,
    private val refreshTokenSharedService: RefreshTokenSharedService,
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

        val accessToken: AccessTokenEntity = accessTokenSharedService.createAccessToken(userDetails)

        if (refreshTokenSharedService.existsByUserId(userEntity.id)) {
            refreshTokenSharedService.deleteByUserId(userEntity.id)
        }

        val refreshToken: RefreshTokenEntity = refreshTokenSharedService.registerRefreshToken(userDetails)

        val authorities: List<String> = userDetails.authorities.map { it.authority }

        ApiAuthenticateResult(
            userEntity,
            accessToken,
            refreshToken,
            authorities
        )
    }

    //TODO: refresh_token ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????ID???????????????????????????????????????????????????????????????????????????????????? ?????????????????? Bearer ?????????????????????????????????
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

        val newAccessToken: AccessTokenEntity = accessTokenSharedService.createAccessToken(user)
        val newRefreshToken: RefreshTokenEntity = refreshTokenSharedService.exchangeRefreshToken(refreshToken)

        RefreshAccessTokenResult(
            newAccessToken,
            newRefreshToken,
            TokenType.BEARER
        )
    }
}
