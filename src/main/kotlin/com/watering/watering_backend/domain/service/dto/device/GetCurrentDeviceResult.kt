package com.watering.watering_backend.domain.service.dto.device

import com.watering.watering_backend.domain.entity.DeviceEntity

data class GetCurrentDeviceResult(
    val device: DeviceEntity
)
