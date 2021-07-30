package com.watering.watering_backend.application.json.parameter.authentication

import java.beans.ConstructorProperties

data class SignupRequest @ConstructorProperties("username", "password", "authorities") constructor (
    val username: String,
    val password: String,
    val authorities: List<String>
)
