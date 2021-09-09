package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.response.`object`.AutomationSetting
import com.watering.watering_backend.application.json.response.`object`.Setting
import com.watering.watering_backend.domain.entity.SettingEntity

fun convertTo(entity: SettingEntity): Setting {
    return Setting(
        waterAmount = entity.waterAmount ?: 0,
        automationSetting = AutomationSetting(
            enabled = entity.automationSetting.enabled,
            interval = entity.automationSetting.interval ?: 0
        )
    )
}
