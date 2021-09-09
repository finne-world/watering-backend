package com.watering.watering_backend.application.json.response.`object`

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("water_amount", "automation_setting")
data class Setting(
    val waterAmount: Int,
    val automationSetting: AutomationSetting
)
