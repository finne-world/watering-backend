package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

data class UserEntity(
    val id: Long,
    val username: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
