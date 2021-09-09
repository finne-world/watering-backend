package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("code", "message")
data class ErrorObject(
    val code: Int,
    val message: String
)
