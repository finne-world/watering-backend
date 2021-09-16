package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.filter.DeviceFilter
import com.watering.watering_backend.domain.entity.form.DeviceForm
import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult

interface DeviceService {
    fun getById(id: Long): DeviceEntity

    fun getDevices(userId: Long, filter: DeviceFilter): List<DeviceEntity>

    fun registerDevice(userId: Long, name: String): RegisterDeviceResult

    fun updateDevice(deviceId: Long, deviceForm: DeviceForm): DeviceEntity

    fun getCurrentDevice(userId: Long): DeviceEntity
}
