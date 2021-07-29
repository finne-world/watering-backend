package com.watering.watering_backend.lib

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.watering.watering_backend.config.property.AuthenticationProperties
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.lib.extension.toDate
import com.watering.watering_backend.lib.extension.withAudience
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JWTProvider(
    private val algorithm: Algorithm,
    private val authenticationProperties: AuthenticationProperties
) {
    fun createToken(username: String): AccessTokenEntity {
        val expiresIn: Long = authenticationProperties.accessToken.exp
        val now: LocalDateTime = LocalDateTime.now()

        val token: String =  JWT.create()
                                .withIssuer(this.authenticationProperties.accessToken.iss)
                                .withAudience(this.authenticationProperties.accessToken.aud)
                                .withSubject(username)
                                .withExpiresAt(now.plusSeconds(expiresIn).toDate())
                                .withIssuedAt(now.toDate())
                                .sign(this.algorithm)

        return AccessTokenEntity(
            token,
            expiresIn
        )
    }
}
