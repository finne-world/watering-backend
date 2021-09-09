package com.watering.watering_backend.lib.extension

inline fun Boolean.runIfTrue(block: () -> Unit): Boolean {
    this.runIf(true) { block() }.also { return it }
}

inline fun Boolean.runIf(bool: Boolean, block: () -> Unit): Boolean {
    if (this == bool) {
        block()
    }
    return this
}
