package com.watering.watering_backend.domain.message

import java.time.LocalDateTime
import java.util.UUID

open class PublishedDeviceMessage (
    val serial: UUID,
    val timestamp: LocalDateTime
)
