package com.watering.watering_backend.application.json.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.`object`.UpdatedValue
import java.util.UUID

@JsonPropertyOrder("status_code", "timestamp", "device_id", "enabled")
class AutoWateringResponse(
    val deviceId: UUID,
    val enabled: UpdatedValue<Boolean>,
): OkResponse()
