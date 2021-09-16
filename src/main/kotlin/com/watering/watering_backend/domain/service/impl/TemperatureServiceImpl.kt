package com.watering.watering_backend.domain.service.impl

import com.amazonaws.services.sqs.model.Message
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.watering.watering_backend.domain.constant.MessageType
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import com.watering.watering_backend.domain.message.TemperatureHistoryMessage
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.domain.repository.TemperatureRepository
import com.watering.watering_backend.domain.service.TemperatureService
import com.watering.watering_backend.lib.QueueUrlResolver
import com.watering.watering_backend.lib.aws.AmazonSQS
import com.watering.watering_backend.lib.extension.convertTo
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemperatureServiceImpl(
    private val logger: Logger,
    private val queueUrlResolver: QueueUrlResolver,
    private val objectMapper: ObjectMapper,
    private val amazonSQS: AmazonSQS,
    private val deviceRepository: DeviceRepository,
    private val temperatureRepository: TemperatureRepository
): TemperatureService {
    @Transactional
    override fun receiveHistoryMessages() {
        val queueUrl: String = this.queueUrlResolver.resolve(MessageType.TEMPERATURE_HISTORY)
        val sqsMessages: List<Message> = this.amazonSQS.receiveMessages(queueUrl).let { it.messages }

        sqsMessages.convertTo(
            converter = { message: Message ->
                this.objectMapper.readValue(message.body, TemperatureHistoryMessage::class.java)
            },
            onFailed = {
                when (it) {
                    is JsonParseException -> this.logger.error("message has invalid content.", it)
                    is JsonMappingException -> this.logger.error("received message is invalid format.", it)
                    else -> this.logger.error("unknown error.", it)
                }
            }
        )
        .let { messages: List<TemperatureHistoryMessage> ->
            this.deviceRepository.getDevicesBySerials(
                messages.map { it.serial }
            )
            .let { devices: List<DeviceEntity> ->
                messages.map { message: TemperatureHistoryMessage ->
                   Pair(devices.first { it.serial == message.serial }, message)
                }
            }
        }
        .also {
            this.temperatureRepository.saveHistories(it)
        }

        sqsMessages.forEach { message ->
            this.amazonSQS.deleteMessage(queueUrl, message.receiptHandle).also {
                this.logger.info("delete message from AmazonSQS. message_id: ${message.messageId}")
            }
        }
    }

    @Transactional
    override fun getHistories(deviceId: Long, limit: Int): List<TemperatureHistoryEntity> {
        return this.temperatureRepository.getHistories(deviceId, limit)
    }
}