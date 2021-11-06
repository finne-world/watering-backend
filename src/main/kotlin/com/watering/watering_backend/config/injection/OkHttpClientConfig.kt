package com.watering.watering_backend.config.injection

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OkHttpClientConfig {
    @Bean
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
