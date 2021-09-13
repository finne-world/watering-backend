package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class AutomationSettingEntity(
    val id: Long,
    val deviceId: Long,
    val enabled: Boolean,
    val interval: Int?,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)
