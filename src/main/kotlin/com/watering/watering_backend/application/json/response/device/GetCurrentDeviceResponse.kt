package com.watering.watering_backend.application.json.response.device

import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.Device

class GetCurrentDeviceResponse(
    val memberId: Long,
    val device: Device
): OkResponse()
