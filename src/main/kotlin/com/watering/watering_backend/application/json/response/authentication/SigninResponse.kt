package com.watering.watering_backend.application.json.response.authentication

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("status_code", "timestamp", "id", "username", "authorities", "access_token", "expires_in", "refresh_token", "token_type")
data class SigninResponse(
    val id: Long,
    val username: String,
    val authorities: List<String>,
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val tokenType: String
)
