package com.watering.watering_backend.domain.service.impl

import com.amazonaws.services.sqs.model.Message
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.watering.watering_backend.config.property.AWSProperties
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.message.WateringHistoryMessage
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.domain.repository.WateringHistoryRepository
import com.watering.watering_backend.domain.service.WateringService
import com.watering.watering_backend.lib.aws.AmazonSQS
import com.watering.watering_backend.lib.extension.convertTo
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WateringServiceImpl(
    private val logger: Logger,
    private val awsProperties: AWSProperties,
    private val amazonSQS: AmazonSQS,
    private val objectMapper: ObjectMapper,
    private val deviceRepository: DeviceRepository,
    private val wateringHistoryRepository: WateringHistoryRepository,
): WateringService {
    @Transactional
    //TODO: どうにか共通化できないだろうか
    override fun receiveHistoryMessages() {
        val queueUrl: String = this.awsProperties.sqs.queues.testUrl

        val messages: List<Message> = this.amazonSQS.receiveMessages(queueUrl).let { it.messages }

        messages.convertTo(
            converter = { message: Message ->
                this.objectMapper.readValue(message.body, WateringHistoryMessage::class.java)
            },
            onFailed = {
                when (it) {
                    is JsonParseException -> this.logger.error("message has invalid content.", it)
                    is JsonMappingException -> this.logger.error("received message is invalid format.", it)
                    else -> this.logger.error("unknown error.", it)
                }
            }
        )
        .let { wateringHistoryMessages: List<WateringHistoryMessage> ->
            this.deviceRepository.getDevicesBySerials(
                wateringHistoryMessages.map { it.serial }
            )
            .mapIndexed { index: Int, deviceEntity: DeviceEntity ->
                Pair(wateringHistoryMessages[index], deviceEntity)
            }
        }
        .also {
            this.wateringHistoryRepository.saveHistories(it)
        }

        messages.forEach { message ->
            this.amazonSQS.deleteMessage(queueUrl, message.receiptHandle).also {
                this.logger.info("delete message from AmazonSQS. message_id: ${message.messageId}")
            }
        }
    }
}
