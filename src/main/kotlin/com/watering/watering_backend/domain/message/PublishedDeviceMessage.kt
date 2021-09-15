package com.watering.watering_backend.domain.message

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
open class PublishedDeviceMessage (
    @Contextual
    val serial: UUID,
    @Contextual
    val timestamp: LocalDateTime
)
