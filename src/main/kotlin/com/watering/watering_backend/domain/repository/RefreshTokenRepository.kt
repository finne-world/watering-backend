package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.time.LocalDateTime
import java.util.UUID

interface RefreshTokenRepository {
    fun create(userId: Long, expiresAt: LocalDateTime): Either<InsertFailedException, RefreshTokenEntity>

    fun updateToken(tokenId: Long, tokenUUID: UUID)

    fun getById(id: Long): Option<RefreshTokenEntity>

    fun getByUserId(userId: Long): Option<RefreshTokenEntity>

    fun getByUUID(tokenUUID: UUID): Option<RefreshTokenEntity>

    fun existsByUserId(userId: Long): Boolean
}
