package com.watering.watering_backend.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("authentication")
data class AuthenticationProperties(
    val accessToken: AccessTokenProperties,
    val refreshToken: RefreshTokenProperties
)

data class AccessTokenProperties(
    val iss: String,
    val aud: List<String>,
    val exp: Long
)

data class RefreshTokenProperties(
    val exp: Long
)
