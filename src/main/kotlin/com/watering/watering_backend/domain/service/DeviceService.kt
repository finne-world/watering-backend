package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.device.GetCurrentDeviceResult
import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult

interface DeviceService {
    fun registerDevice(memberId: Long, name: String): RegisterDeviceResult

    fun getCurrentDevice(memberId: Long): GetCurrentDeviceResult
}
