package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

class DeviceEntity(
    val id: Long,
    val serial: UUID,
    val userId: Long,
    val name: String,
    val current: Boolean,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)
