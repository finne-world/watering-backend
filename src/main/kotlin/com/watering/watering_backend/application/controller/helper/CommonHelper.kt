package com.watering.watering_backend.application.controller.helper

fun redirect(url: String): String {
    return "redirect:${if (url.startsWith("/")) url else "/${url}"}"
}
