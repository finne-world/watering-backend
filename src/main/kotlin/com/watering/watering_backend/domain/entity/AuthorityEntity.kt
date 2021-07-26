package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

data class AuthorityEntity(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
