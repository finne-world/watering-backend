package com.watering.watering_backend.application.json.response.auto_watering

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.UpdatedValue
import java.util.UUID

@JsonPropertyOrder("status_code", "timestamp", "device_id", "enabled")
class ChangeEnabledResponse(
    val deviceId: UUID,
    val enabled: UpdatedValue<Boolean>,
): OkResponse()
