package com.watering.watering_backend.domain.service.impl

import com.amazonaws.services.sqs.model.Message
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.watering.watering_backend.domain.constant.MessageType
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.message.HumidityHistoryMessage
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.domain.repository.HumidityRepository
import com.watering.watering_backend.domain.service.HumidityService
import com.watering.watering_backend.lib.QueueUrlResolver
import com.watering.watering_backend.lib.aws.AmazonSQS
import com.watering.watering_backend.lib.extension.convertTo
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HumidityServiceImpl(
    private val logger: Logger,
    private val amazonSQS: AmazonSQS,
    private val objectMapper: ObjectMapper,
    private val queueUrlResolver: QueueUrlResolver,
    private val deviceRepository: DeviceRepository,
    private val humidityRepository: HumidityRepository
): HumidityService {
    //TODO: 共通化したい
    @Transactional
    override fun receiveHistoryMessages() {
        val queueUrl: String = this.queueUrlResolver.resolve(MessageType.HUMIDITY_HISTORY).also { println(it) }
        val sqsMessages: List<Message> = this.amazonSQS.receiveMessages(queueUrl).let { it.messages }

        sqsMessages.convertTo(
            converter = { message: Message ->
                this.objectMapper.readValue(message.body, HumidityHistoryMessage::class.java)
            },
            onFailed = {
                when (it) {
                    is JsonParseException -> this.logger.error("message has invalid content.", it)
                    is JsonMappingException -> this.logger.error("received message is invalid format.", it)
                    else -> this.logger.error("unknown error.", it)
                }
            }
        )
        .let { messages: List<HumidityHistoryMessage> ->
            this.deviceRepository.getDevicesBySerials(
                messages.map { it.serial }
            )
            .let { devices: List<DeviceEntity> ->
                messages.map { message: HumidityHistoryMessage ->
                    Pair(devices.first { it.serial == message.serial }, message)
                }
            }
        }
        .also {
            this.humidityRepository.saveHistories(it)
        }

        sqsMessages.forEach { message ->
            this.amazonSQS.deleteMessage(queueUrl, message.receiptHandle).also {
                this.logger.info("delete message from AmazonSQS. message_id: ${message.messageId}")
            }
        }
    }
}
