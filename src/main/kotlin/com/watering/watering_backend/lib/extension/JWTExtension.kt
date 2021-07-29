package com.watering.watering_backend.lib.extension

import com.auth0.jwt.JWTCreator
import com.auth0.jwt.interfaces.Verification

fun Verification.withAudience(audiences: List<String>): Verification {
    return this.withAudience(*audiences.toTypedArray())
}

fun JWTCreator.Builder.withAudience(audiences: List<String>): JWTCreator.Builder {
    return this.withAudience(*audiences.toTypedArray())
}
