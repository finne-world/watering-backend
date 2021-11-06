package com.watering.watering_backend.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("oauth")
data class OauthProperties(
    val discord: Discord,
)

data class Discord(
    val authorizeUrl: String,
    val clientId: String,
    val redirectUri: String
)
