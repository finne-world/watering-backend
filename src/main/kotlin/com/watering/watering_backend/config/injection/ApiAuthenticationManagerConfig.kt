package com.watering.watering_backend.config.injection

import com.watering.watering_backend.config.MultiHttpSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager

@Configuration
class ApiAuthenticationManagerConfig(
    private val webSecurityConfigurerAdapter: MultiHttpSecurityConfig.ApiWebSecurityConfigurerAdapter
) {
    @Bean
    fun getAuthenticationManager(): AuthenticationManager {
        return this.webSecurityConfigurerAdapter.authenticationManagerBean()
    }
}