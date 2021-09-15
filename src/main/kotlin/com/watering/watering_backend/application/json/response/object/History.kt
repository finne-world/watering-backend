package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("value", "timestamp")
data class History<E>(
    val value: E,
    val timestamp: LocalDateTime
)
