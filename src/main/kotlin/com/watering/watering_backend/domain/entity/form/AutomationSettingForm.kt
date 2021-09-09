package com.watering.watering_backend.domain.entity.form

import com.watering.watering_backend.domain.entity.AutomationSettingEntity

data class AutomationSettingForm(
    val enabled: Boolean?,
    val interval: Int?
): Form<AutomationSettingEntity>() {
    override fun getUnmodifiableProperties(entity: AutomationSettingEntity): Map<PropertyName, FormEntityValueMapping> {
        return mapOf()
    }
}
