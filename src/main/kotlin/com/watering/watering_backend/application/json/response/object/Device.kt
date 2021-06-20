package com.watering.watering_backend.application.json.response.`object`

import java.util.UUID

data class Device(
    val id: UUID,
    val name: String,
    val current: Boolean
)
