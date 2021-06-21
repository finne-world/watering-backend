package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.response.`object`.Device
import com.watering.watering_backend.domain.entity.DeviceEntity

fun convertTo(value: DeviceEntity): Device {
    return Device(
        id = value.id,
        name = value.name,
        current = value.current
    )
}
