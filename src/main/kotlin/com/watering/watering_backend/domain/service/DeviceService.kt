package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult

interface DeviceService {
    fun registerDevice(memberId: Long, name: String): RegisterDeviceResult
}
