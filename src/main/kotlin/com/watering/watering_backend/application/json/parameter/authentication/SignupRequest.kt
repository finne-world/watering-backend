package com.watering.watering_backend.application.json.parameter.authentication

data class SignupRequest(
    val username: String,
    val password: String,
    val authorities: List<String>
)
