package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

abstract class TableBasedEntity(
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
