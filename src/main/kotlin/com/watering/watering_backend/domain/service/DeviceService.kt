package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.device.GetCurrentDeviceResult
import com.watering.watering_backend.domain.service.dto.device.GetDevicesResult
import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult

interface DeviceService {
    fun getDevices(memberId: Long): GetDevicesResult

    fun registerDevice(memberId: Long, name: String): RegisterDeviceResult

    fun getCurrentDevice(memberId: Long): GetCurrentDeviceResult
}
