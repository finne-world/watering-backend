package com.watering.watering_backend.application.json.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.`object`.Setting
import java.util.UUID

@JsonPropertyOrder("status_code", "timestamp", "device_id", "setting")
class DevicesAddResponse(
    val deviceId: UUID,
    val setting: Setting
): OkResponse()
