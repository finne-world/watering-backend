package com.watering.watering_backend.domain.service.shared

import arrow.core.Option
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

interface RefreshTokenSharedService {
    fun getByTokenUUID(tokenUUID: UUID): Option<RefreshTokenEntity>

    fun registerRefreshToken(userDetails: UserDetails): RefreshTokenEntity

    fun exchangeRefreshToken(userId: Long): RefreshTokenEntity

    fun exchangeRefreshToken(refreshToken: RefreshTokenEntity): RefreshTokenEntity

    fun existsByUserId(userId: Long): Boolean

    fun deleteByUserId(userId: Long): Boolean
}