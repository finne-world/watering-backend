package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.parameter.device.UpdateDeviceRequest
import com.watering.watering_backend.application.json.response.`object`.Device
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.form.DeviceForm

fun convertTo(entity: DeviceEntity): Device {
    return Device(
        id = entity.id,
        name = entity.name,
        uuid = entity.uuid,
        current = entity.current
    )
}

fun UpdateDeviceRequest.convertToForm(): DeviceForm {
    return DeviceForm(
        this.id,
        this.uuid,
        this.userId,
        this.name,
        this.current
    )
}
