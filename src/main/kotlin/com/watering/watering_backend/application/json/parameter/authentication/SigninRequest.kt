package com.watering.watering_backend.application.json.parameter.authentication

import java.beans.ConstructorProperties

data class SigninRequest @ConstructorProperties("username", "password") constructor (
    val username: String,
    val password: String
)
