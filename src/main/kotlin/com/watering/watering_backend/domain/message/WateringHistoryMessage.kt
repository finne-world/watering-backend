package com.watering.watering_backend.domain.message

import java.beans.ConstructorProperties
import java.time.LocalDateTime
import java.util.UUID

data class WateringHistoryMessage
@ConstructorProperties(
    "serial",
    "amount",
    "timestamp"
) constructor(
    val serial: UUID,
    val amount: Int,
    val timestamp: LocalDateTime
)
