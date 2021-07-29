package com.watering.watering_backend.application.json.parameter.authentication

import java.util.UUID

data class RefreshTokenRequest(
    val refreshToken: UUID
)
