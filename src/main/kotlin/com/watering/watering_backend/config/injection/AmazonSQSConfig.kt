package com.watering.watering_backend.config.injection

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonSQSConfig {
    @Bean
    fun getAmazonSQS(): AmazonSQS {
        return AmazonSQSClientBuilder.defaultClient()
    }
}
