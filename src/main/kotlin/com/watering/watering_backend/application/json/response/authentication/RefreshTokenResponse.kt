package com.watering.watering_backend.application.json.response.authentication

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("status_code", "timestamp", "access_token", "expires_in", "refresh_token", "token_type")
data class RefreshTokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val tokenType: String
)
