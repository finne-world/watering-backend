package com.watering.watering_backend.application.json.response.user

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.User

@JsonPropertyOrder("status_code", "timestamp", "users")
data class GetUsersResponse(
    val users: List<User>
): OkResponse()
