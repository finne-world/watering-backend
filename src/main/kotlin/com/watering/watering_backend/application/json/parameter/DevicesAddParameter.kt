package com.watering.watering_backend.application.json.parameter

import java.beans.ConstructorProperties

data class DevicesAddParameter @ConstructorProperties("member_id", "device_name") constructor (
    val memberId: Long,
    val deviceName: String
)
