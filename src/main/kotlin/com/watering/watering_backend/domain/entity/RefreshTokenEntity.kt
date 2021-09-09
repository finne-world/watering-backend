package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

class RefreshTokenEntity(
    val id: Long,
    val userId: Long,
    val token: UUID,
    val expiresAt: LocalDateTime,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)

fun RefreshTokenEntity.isExpired(): Boolean {
    return this.expiresAt.isBefore(LocalDateTime.now())
}
