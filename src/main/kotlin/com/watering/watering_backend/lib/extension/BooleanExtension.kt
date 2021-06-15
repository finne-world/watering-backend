package com.watering.watering_backend.lib.extension

fun Boolean.runIfTrue(block: () -> Unit) {
    if (this) {
        block()
    }
}
