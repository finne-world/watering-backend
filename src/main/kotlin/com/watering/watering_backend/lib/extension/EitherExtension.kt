package com.watering.watering_backend.lib.extension

import arrow.core.Either
import arrow.core.getOrHandle

fun <A: Throwable, B> Either<A, B>.getOrThrow(): B = getOrHandle { throw it }
