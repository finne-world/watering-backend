package com.watering.watering_backend.application.json.parameter.device

import java.beans.ConstructorProperties

data class RegisterDeviceParameter @ConstructorProperties("member_id", "device_name") constructor (
    val memberId: Long,
    val deviceName: String
)
