package com.watering.watering_backend.application.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class WateringSettingEntity(
    val id: Long,
    val deviceId: UUID,
    val autoWatering: Boolean,
    val interval: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
