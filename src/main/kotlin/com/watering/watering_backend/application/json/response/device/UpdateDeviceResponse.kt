package com.watering.watering_backend.application.json.response.device

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.Device

@JsonPropertyOrder("status_code", "timestamp", "device")
data class UpdateDeviceResponse(
    val device: Device
): OkResponse()
