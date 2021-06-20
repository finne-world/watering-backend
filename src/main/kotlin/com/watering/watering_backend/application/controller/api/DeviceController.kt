package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.json.parameter.DevicesAddParameter
import com.watering.watering_backend.application.json.response.DevicesAddResponse
import com.watering.watering_backend.application.json.response.`object`.AutoWateringSetting
import com.watering.watering_backend.application.json.response.`object`.WateringSetting
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import org.slf4j.Logger
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val logger: Logger,
    private val deviceService: DeviceService
) {
    @PostMapping
    fun register(@ModelAttribute devicesAddParameter: DevicesAddParameter): DevicesAddResponse {
        val (createdDevice: DeviceEntity,
             createdWateringSetting: WateringSettingEntity,
             createdAutoWateringSetting: AutoWateringSettingEntity
        ) = this.deviceService.registerDevice(
            devicesAddParameter.memberId,
            devicesAddParameter.deviceName
        )

        return DevicesAddResponse(
            deviceId = createdDevice.id,
            wateringSetting = WateringSetting(
                waterAmount = createdWateringSetting.waterAmount ?: 0
            ),
            autoWateringSetting = AutoWateringSetting(
                enabled = createdAutoWateringSetting.enabled,
                interval = createdAutoWateringSetting.interval ?: 0
            )
        )
    }
}
