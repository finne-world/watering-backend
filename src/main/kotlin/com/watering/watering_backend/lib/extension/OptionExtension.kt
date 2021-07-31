package com.watering.watering_backend.lib.extension

import arrow.core.Option
import arrow.core.getOrElse

fun <E> Option<E>.get(): E {
    return this.getOrElse { throw IllegalStateException("arrow.core.Option: get() failed. require not None.") }
}
