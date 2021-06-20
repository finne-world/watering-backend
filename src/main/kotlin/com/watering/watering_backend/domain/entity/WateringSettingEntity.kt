package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class WateringSettingEntity(
    val id: Long,
    val deviceId: UUID,
    val waterAmount: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
