package com.watering.watering_backend.lib.extension

fun Throwable.getErrorDescription(): String {
    return this.message!!
}
