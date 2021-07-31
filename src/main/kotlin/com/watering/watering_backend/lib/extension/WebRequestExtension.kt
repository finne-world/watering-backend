package com.watering.watering_backend.lib.extension

import org.springframework.web.context.request.WebRequest

fun WebRequest.getRequestURI(): String {
    return this.getDescription(false).removePrefix("uri=")
}
