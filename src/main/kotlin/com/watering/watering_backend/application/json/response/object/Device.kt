package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.UUID

@JsonPropertyOrder("id", "name", "current")
data class Device(
    val id: Long,
    val serial: UUID,
    val name: String,
    val current: Boolean
)
