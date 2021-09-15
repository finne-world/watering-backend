package com.watering.watering_backend.application.json.response.history

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.History

@JsonPropertyOrder("status_code", "timestamp", "device_id", "histories")
data class WateringHistoryResponse(
    val deviceId: Long,
    val histories: List<History<Int>>
): OkResponse()
