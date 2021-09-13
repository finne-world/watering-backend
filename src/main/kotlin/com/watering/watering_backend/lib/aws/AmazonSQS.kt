package com.watering.watering_backend.lib.aws

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import com.amazonaws.services.sqs.model.ReceiveMessageResult
import com.watering.watering_backend.config.property.AWSProperties
import org.springframework.stereotype.Component

@Component
class AmazonSQS(
    private val amazonSQS: AmazonSQS,
    private val awsProperties: AWSProperties
) {
    fun receiveMessages(queueUrl: String): ReceiveMessageResult {
        this.amazonSQS.receiveMessage(
            ReceiveMessageRequest(queueUrl).also {
                it.waitTimeSeconds = this.awsProperties.sqs.waitTimeSeconds
                it.maxNumberOfMessages = this.awsProperties.sqs.maxNumberOfMessages
            }
        )
        .also {
            return it
        }
    }

    fun deleteMessage(queueUrl: String, receiptHandle: String) {
        this.amazonSQS.deleteMessage(queueUrl, receiptHandle)
    }
}
