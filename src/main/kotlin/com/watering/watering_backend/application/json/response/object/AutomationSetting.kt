package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("enabled", "interval")
data class AutomationSetting(
    val enabled: Boolean,
    val interval: Int
)
