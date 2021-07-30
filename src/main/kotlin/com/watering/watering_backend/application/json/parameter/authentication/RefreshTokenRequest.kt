package com.watering.watering_backend.application.json.parameter.authentication

import java.beans.ConstructorProperties
import java.util.UUID

data class RefreshTokenRequest @ConstructorProperties("refresh_token") constructor (
    val refreshToken: UUID
)
