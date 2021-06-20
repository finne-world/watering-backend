package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class DeviceEntity(
    val id: UUID,
    val name: String,
    val current: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
