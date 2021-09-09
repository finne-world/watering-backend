package com.watering.watering_backend.application.json.parameter.device

import java.beans.ConstructorProperties

data class RegisterDeviceRequest @ConstructorProperties("device_name") constructor (
    val deviceName: String
)
