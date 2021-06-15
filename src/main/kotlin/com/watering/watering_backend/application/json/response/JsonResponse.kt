package com.watering.watering_backend.application.json.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("status_code", "timestamp")
abstract class JsonResponse(
    val statusCode: Int,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
