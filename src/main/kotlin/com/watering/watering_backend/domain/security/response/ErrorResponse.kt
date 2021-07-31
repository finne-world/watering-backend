package com.watering.watering_backend.domain.security.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("status_code", "timestamp", "description", "path")
data class ErrorResponse(
    val statusCode: Int,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val description: String,
    val path: String
)
