package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class RefreshTokenEntity(
    val id: Long,
    val userId: Long,
    val token: UUID,
    val expiresAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

fun RefreshTokenEntity.isExpired(): Boolean {
    return this.expiresAt.isBefore(LocalDateTime.now())
}
