package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.UUID

@JsonPropertyOrder("id", "name", "uuid", "current")
data class Device(
    val id: Long,
    val name: String,
    val uuid: UUID,
    val current: Boolean
)
