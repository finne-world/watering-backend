package com.watering.watering_backend.application

enum class TemplateMapping(
    val value: String
) {
    USER_CREATE("front/users/signup"),
    USER_CREATE_COMPLETED("front/users/signup/completed"),
    USER_SIGNIN("front/users/signin"),
    USER_PROFILE("front/users/profile");
}
