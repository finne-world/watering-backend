package com.watering.watering_backend.lib

import com.watering.watering_backend.config.property.AWSProperties
import com.watering.watering_backend.domain.constant.MessageType
import org.springframework.stereotype.Component

interface QueueUrlResolver {
    fun resolve(type: MessageType): String
}

@Component
class QueueUrlResolverImpl(
    private val awsProperties: AWSProperties
): QueueUrlResolver {
    override fun resolve(type: MessageType): String {
        return when (type) {
            MessageType.WATERING_HISTORY -> this.awsProperties.sqs.queues.wateringHistory
            MessageType.TEMPERATURE_HISTORY -> this.awsProperties.sqs.queues.temperatureHistory
            MessageType.HUMIDITY_HISTORY -> this.awsProperties.sqs.queues.humidityHistory
        }
    }
}
