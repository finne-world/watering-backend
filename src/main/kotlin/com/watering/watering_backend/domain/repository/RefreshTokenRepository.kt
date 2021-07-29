package com.watering.watering_backend.domain.repository

import arrow.core.Either
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.time.LocalDateTime

interface RefreshTokenRepository {
    fun create(userId: Long, expiresAt: LocalDateTime): Either<InsertFailedException, RefreshTokenEntity>

    fun existsByUserId(userId: Long): Boolean

    fun deleteByUserId(userId: Long): Boolean
}
