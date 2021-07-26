package com.watering.watering_backend.config.injection

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.watering.watering_backend.config.property.AuthenticationProperties
import com.watering.watering_backend.lib.extension.withAudience
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JWTVerifierConfig(
    private val algorithm: Algorithm,
    private val authenticationProperties: AuthenticationProperties
) {

    @Bean
    fun getJWTVerifier(): JWTVerifier {
        return JWT.require(this.algorithm)
                  .withIssuer(this.authenticationProperties.accessToken.iss)
                  .withAudience(this.authenticationProperties.accessToken.aud)
                  .build()
    }
}
