package com.watering.watering_backend.application.json.response.device

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.Device

@JsonPropertyOrder("status_code", "timestamp", "user_id", "devices")
data class GetDevicesResponse(
    val userId: Long,
    val devices: List<Device>
): OkResponse()
