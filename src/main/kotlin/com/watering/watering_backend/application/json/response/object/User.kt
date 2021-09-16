package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("id", "name", "authorities")
data class User(
    val id: Long,
    val name: String,
    val authorities: List<String>,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val discordId: Long?
)
