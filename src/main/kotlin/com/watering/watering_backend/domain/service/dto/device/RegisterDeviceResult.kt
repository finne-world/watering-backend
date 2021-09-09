package com.watering.watering_backend.domain.service.dto.device

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.SettingEntity

data class RegisterDeviceResult(
    val deviceEntity: DeviceEntity,
    val settingEntity: SettingEntity
)
