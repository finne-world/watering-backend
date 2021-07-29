package com.watering.watering_backend.application.json.response.authentication

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse

@JsonPropertyOrder("status_code", "timestamp", "id", "username", "authorities")
data class SignupResponse(
    val id: Long,
    val username: String,
    val authorities: List<String>,
): OkResponse()
