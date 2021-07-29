package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

data class UserDiscordMapEntity(
    val id: Long,
    val userId: Long,
    val discordId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
