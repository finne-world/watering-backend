package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class SettingEntity(
    val id: Long,
    val deviceId: Long,
    val waterAmount: Int?,
    val automationSetting: AutomationSettingEntity,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)
