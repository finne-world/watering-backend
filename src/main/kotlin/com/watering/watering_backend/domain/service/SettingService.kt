package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.SettingForm

interface SettingService {
    fun getByDeviceId(deviceId: Long): SettingEntity

    fun update(deviceId: Long, settingForm: SettingForm): SettingEntity
}
