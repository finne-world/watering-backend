package com.watering.watering_backend.lib.extension

import arrow.core.Either
import arrow.core.getOrHandle

fun <T: Throwable, R> Either<T, R>.getOrThrow(): R = getOrHandle { throw it }
