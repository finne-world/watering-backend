package com.watering.watering_backend.application.json.parameter.device

import java.beans.ConstructorProperties

data class GetCurrentDeviceRequest @ConstructorProperties("user_id") constructor(
    val userId: Long
)
