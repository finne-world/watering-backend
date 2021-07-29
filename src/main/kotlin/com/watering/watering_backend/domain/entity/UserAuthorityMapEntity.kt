package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

data class UserAuthorityMapEntity(
    val id: Long,
    val userId: Long,
    val authorityId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
