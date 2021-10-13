package com.watering.watering_backend.application

import org.springframework.http.HttpMethod

object ApplicationUrl {
    val GET_USER_SIGNUP: Url = Url(HttpMethod.GET, "/users/signup")
    val POST_USER_SIGNUP: Url = Url(HttpMethod.POST, "/users/signup")
    val GET_USER_SIGNUP_COMPLETED: Url = Url(HttpMethod.GET, "/users/signup/completed")
    val GET_USER_SIGNIN: Url = Url(HttpMethod.GET, "/users/signin")
    val POST_USER_SIGNIN: Url = Url(HttpMethod.POST, "/users/signin")
    val GET_USER_PROFILE: Url = Url(HttpMethod.GET, "/users/profile")

    data class Url(
        val method: HttpMethod,
        val url: String
    )
}
