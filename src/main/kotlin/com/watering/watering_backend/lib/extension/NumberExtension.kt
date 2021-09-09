package com.watering.watering_backend.lib.extension

fun Int.isPositive(): Boolean {
    return this > 0
}

inline fun Int.ifNegative(block: () -> Unit): Int {
    (this < 0).runIfTrue(block).also { return this }
}
