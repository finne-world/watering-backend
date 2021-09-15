package com.watering.watering_backend.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("aws")
data class AWSProperties(
    val parameterStore: ParameterStoreProperties,
    val sqs: SqsProperties
)

data class ParameterStoreProperties(
    val jwt: JwtProperties
)

data class JwtProperties(
    val publicKey: String,
    val privateKey: String
)

data class SqsProperties(
    val queues: QueuesProperties,
    val waitTimeSeconds: Int,
    val maxNumberOfMessages: Int
)

data class QueuesProperties(
    val testUrl: String,
    val wateringHistory: String
    val humidityHistory: String
)
