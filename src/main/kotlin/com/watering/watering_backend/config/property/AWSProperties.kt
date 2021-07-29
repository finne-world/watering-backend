package com.watering.watering_backend.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws")
data class AWSProperties(
    val parameterStore: ParameterStoreProperties,
)

data class ParameterStoreProperties(
    val jwt: JwtProperties
)

data class JwtProperties(
    val publicKey: String,
    val privateKey: String
)
