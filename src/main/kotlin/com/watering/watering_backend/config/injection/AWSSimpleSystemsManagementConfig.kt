package com.watering.watering_backend.config.injection

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSSimpleSystemsManagementConfig {
    @Bean
    fun getAWSSimpleSystemsManagement(): AWSSimpleSystemsManagement {
        return AWSSimpleSystemsManagementClientBuilder.defaultClient()
    }
}
