package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class AutoWateringSettingEntity(
    val id: Long,
    val deviceId: UUID,
    val enabled: Boolean,
    val interval: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
