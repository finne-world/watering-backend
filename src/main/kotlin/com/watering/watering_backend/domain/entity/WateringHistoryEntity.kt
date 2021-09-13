package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class WateringHistoryEntity(
    val id: Long,
    val deviceId: Long,
    val amount: Int,
    val timestamp: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
