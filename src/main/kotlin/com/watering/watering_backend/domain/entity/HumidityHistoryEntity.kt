package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class HumidityHistoryEntity(
    val id: Long,
    val deviceId: Long,
    val value: Float,
    val timestamp: LocalDateTime,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(
    createdAt,
    updatedAt
)
