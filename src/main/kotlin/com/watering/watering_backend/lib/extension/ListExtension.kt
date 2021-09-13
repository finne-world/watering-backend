package com.watering.watering_backend.lib.extension

fun <T, R> List<T>.convertTo(
    converter: (T) -> R,
    recover: ((Throwable) -> R)? = null,
    onFailed: (Throwable) -> Unit = { throw it }
): List<R> {
    val converted: MutableList<R> = mutableListOf()
    for (item: T in this) {
        try {
            converted.add(converter(item))
        }
        catch (throwable: Throwable) {
            if (recover != null) {
                recover(throwable).also {
                    converted.add(it)
                }
            }
            else {
                onFailed(throwable)
            }
        }
    }

    return converted
}
