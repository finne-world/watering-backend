package com.watering.watering_backend.domain.service.shared.impl

import com.watering.watering_backend.config.property.AuthenticationProperties
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.RefreshTokenRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.shared.RefreshTokenSharedService
import com.watering.watering_backend.domain.service.shared.dto.refresh_token.RegisterRefreshTokenResult
import com.watering.watering_backend.lib.extension.getOrThrow
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RefreshTokenSharedServiceImpl(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationProperties: AuthenticationProperties
): RefreshTokenSharedService {
    override fun registerRefreshToken(userDetails: UserDetails): RegisterRefreshTokenResult = transaction {
        val user: UserEntity = userRepository.getByUsername(userDetails.username)
                                             .toEither { ResourceNotFoundException("User resource not found. username=${userDetails.username}") }
                                             .getOrThrow()

        if (refreshTokenRepository.existsByUserId(user.id)) {
            refreshTokenRepository.deleteByUserId(user.id)
        }

        val refreshToken: RefreshTokenEntity = refreshTokenRepository.create(
            user.id,
            LocalDateTime.now().plusSeconds(authenticationProperties.refreshToken.exp)
        ).getOrThrow()

        RegisterRefreshTokenResult(
            refreshToken
        )
    }
}
