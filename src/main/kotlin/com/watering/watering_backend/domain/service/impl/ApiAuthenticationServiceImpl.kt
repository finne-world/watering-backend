package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrHandle
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.UserRegistrationFailedException
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.ApiAuthenticationService
import com.watering.watering_backend.domain.service.dto.authentication.ApiAuthenticateResult
import com.watering.watering_backend.domain.service.dto.authentication.ApiRegisterUserResult
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.domain.service.shared.RefreshTokenSharedService
import com.watering.watering_backend.lib.extension.getOrThrow
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

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
                                                   .toEither { ResourceNotFoundException("User resource not found. username=${userDetails.username}") }
                                                   .getOrThrow()
        val (accessToken: AccessTokenEntity) = accessTokenSharedService.createAccessToken(userDetails)
        val (refreshToken: RefreshTokenEntity) = refreshTokenSharedService.registerRefreshToken(userDetails)
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
            throw UserRegistrationFailedException("the user with username [${username}] already exists. please choice another username.")
        }

        val authorityEntities: List<AuthorityEntity> = authorityRepository.getAuthoritiesByNameList(authorities).getOrHandle {
            throw UserRegistrationFailedException("Failed to create the user with authority [${authorities.joinToString(", ")}]. Authority [${it.invalidAuthorityNames.joinToString(", ")}] does not exist.")
        }

        val createdUser: UserEntity = userRepository.create(
            username,
            passwordEncoder.encode(password),
            authorityEntities
        ).getOrHandle {
            throw UserRegistrationFailedException("Failed to create the user.")
        }

        ApiRegisterUserResult(
            createdUser,
            authorityEntities
        )
    }
}
