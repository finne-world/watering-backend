package com.watering.watering_backend.domain.constant

enum class TemplateMapping(
    val value: String
) {
    USER_CREATE("front/users/create"),
    USER_CREATE_COMPLETED("front/users/create/completed");

    fun redirect(): String {
        return "redirect:/${this.value}"
    }
}
