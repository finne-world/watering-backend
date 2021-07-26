package com.watering.watering_backend.lib.extension

inline fun Boolean.runIfTrue(block: () -> Unit) {
    this.runIf(true) { block() }
}

inline fun Boolean.runIf(bool: Boolean, block: () -> Unit) {
    if (this == bool) {
        block()
    }
}
