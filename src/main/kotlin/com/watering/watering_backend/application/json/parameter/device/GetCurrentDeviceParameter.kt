package com.watering.watering_backend.application.json.parameter.device

import java.beans.ConstructorProperties

data class GetCurrentDeviceParameter @ConstructorProperties("member_id") constructor(
    val memberId: Long
)
