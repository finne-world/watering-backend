package com.watering.watering_backend.lib.extension

fun Boolean.runIfTrue(block: () -> Unit) {
    this.runIf(true) { block() }
}

fun Boolean.runIf(bool: Boolean, block: () -> Unit) {
    if (this == bool) {
        block()
    }
}
