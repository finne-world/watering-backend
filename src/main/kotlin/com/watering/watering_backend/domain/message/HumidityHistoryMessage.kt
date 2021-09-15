package com.watering.watering_backend.domain.message

import java.beans.ConstructorProperties
import java.time.LocalDateTime
import java.util.UUID

class HumidityHistoryMessage
@ConstructorProperties(
    "serial",
    "timestamp",
    "value"
) constructor(
    serial: UUID,
    timestamp: LocalDateTime,
    val value: Float
): PublishedDeviceMessage(
    serial,
    timestamp
)
