package com.watering.watering_backend.application.json.response.device

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.AutoWateringSetting
import com.watering.watering_backend.application.json.response.`object`.WateringSetting
import java.util.UUID

@JsonPropertyOrder("status_code", "timestamp", "device_id", "watering_setting", "auto_watering_setting")
class RegisterDeviceResponse(
    val deviceId: UUID,
    val wateringSetting: WateringSetting,
    val autoWateringSetting: AutoWateringSetting
): OkResponse()
