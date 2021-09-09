package com.watering.watering_backend.domain.entity.form

import com.watering.watering_backend.domain.entity.SettingEntity

data class SettingForm(
    val deviceId: Long?,
    val waterAmount: Int?,
    val automationSetting: AutomationSettingForm?
): Form<SettingEntity>() {
    override fun getUnmodifiableProperties(entity: SettingEntity): Map<PropertyName, FormEntityValueMapping> {
        return mapOf(
            PropertyName("device_id") to FormEntityValueMapping(this.deviceId, entity.deviceId),
        )
    }
}
