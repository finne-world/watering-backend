package com.watering.watering_backend.domain.service.shared.impl

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.left
import arrow.core.right
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.watering.watering_backend.config.property.AuthenticationProperties
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.lib.extension.toDate
import com.watering.watering_backend.lib.extension.verifyToken
import com.watering.watering_backend.lib.extension.withAudience
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AccessTokenSharedServiceImpl(
    private val algorithm: Algorithm,
    private val authenticationProperties: AuthenticationProperties
): AccessTokenSharedService {
    override fun createAccessToken(userDetails: UserDetails): AccessTokenEntity {
        return this.createToken(userDetails.username)
    }

    override fun createAccessToken(userEntity: UserEntity): AccessTokenEntity {
        return this.createToken(userEntity.username)
    }

    override fun verifyAccessToken(token: String): Either<Throwable, AccessTokenEntity> {
        val jwtVerifier: JWTVerifier = JWT.require(this.algorithm)
                                          .withIssuer(this.authenticationProperties.accessToken.iss)
                                          .withAudience(this.authenticationProperties.accessToken.aud)
                                          .build()

        val decodedJWT: DecodedJWT = jwtVerifier.verifyToken(token).getOrHandle { return it.left() }

        return AccessTokenEntity(
            token,
            decodedJWT.subject,
            this.authenticationProperties.accessToken.exp
        ).right()
    }

    private fun createToken(username: String): AccessTokenEntity {
        val expiresIn: Long = this.authenticationProperties.accessToken.exp
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
            username,
            expiresIn
        )
    }
}
