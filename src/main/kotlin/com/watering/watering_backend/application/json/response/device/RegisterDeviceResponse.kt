package com.watering.watering_backend.application.json.response.device

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.Setting
import java.util.UUID

@JsonPropertyOrder("status_code", "timestamp", "id", "name", "setting")
class RegisterDeviceResponse(
    val id: Long,
    val serial: UUID,
    val name: String,
    val setting: Setting,
): OkResponse()
