package com.watering.watering_backend.domain.service.dto.device

import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringSettingEntity

data class RegisterDeviceResult(
    val createdDevice: DeviceEntity,
    val createdWateringSetting: WateringSettingEntity,
    val createdAutoWateringSetting: AutoWateringSettingEntity
)
