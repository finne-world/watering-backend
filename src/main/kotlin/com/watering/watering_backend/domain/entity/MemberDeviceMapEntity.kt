package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class MemberDeviceMapEntity(
    val id: Long,
    val memberId: Long,
    val deviceId: UUID,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
