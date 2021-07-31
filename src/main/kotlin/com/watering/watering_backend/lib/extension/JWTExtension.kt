package com.watering.watering_backend.lib.extension

import arrow.core.Either
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.auth0.jwt.interfaces.Verification

fun Verification.withAudience(audiences: List<String>): Verification {
    return this.withAudience(*audiences.toTypedArray())
}

fun JWTCreator.Builder.withAudience(audiences: List<String>): JWTCreator.Builder {
    return this.withAudience(*audiences.toTypedArray())
}

fun JWTVerifier.verifyToken(token: String): Either<Throwable, DecodedJWT> {
    return Either.catch {
        this.verify(token)
    }
}
