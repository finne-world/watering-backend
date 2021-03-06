package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

open class TableBasedEntity(
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
