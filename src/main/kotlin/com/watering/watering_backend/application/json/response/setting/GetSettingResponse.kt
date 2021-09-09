package com.watering.watering_backend.application.json.response.setting

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.OkResponse
import com.watering.watering_backend.application.json.response.`object`.Setting

@JsonPropertyOrder("status_code", "timestamp", "device_id", "setting")
class GetSettingResponse(
    val deviceId: Long,
    val setting: Setting
): OkResponse()
