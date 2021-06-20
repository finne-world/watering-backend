package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.json.parameter.device.RegisterDeviceParameter
import com.watering.watering_backend.application.json.parameter.device.GetCurrentDeviceParameter
import com.watering.watering_backend.application.json.response.DevicesAddResponse
import com.watering.watering_backend.application.json.response.`object`.AutoWateringSetting
import com.watering.watering_backend.application.json.response.`object`.Device
import com.watering.watering_backend.application.json.response.`object`.WateringSetting
import com.watering.watering_backend.application.json.response.device.GetCurrentDeviceResponse
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
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
    fun register(@ModelAttribute registerDeviceParameter: RegisterDeviceParameter): DevicesAddResponse {
        val (createdDevice: DeviceEntity,
             createdWateringSetting: WateringSettingEntity,
             createdAutoWateringSetting: AutoWateringSettingEntity
        ) = this.deviceService.registerDevice(
            registerDeviceParameter.memberId,
            registerDeviceParameter.deviceName
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

    @GetMapping("current")
    fun getCurrentDevice(@ModelAttribute getCurrentDeviceParameter: GetCurrentDeviceParameter): GetCurrentDeviceResponse {
        val (currentDevice: DeviceEntity) = deviceService.getCurrentDevice(getCurrentDeviceParameter.memberId)

        return GetCurrentDeviceResponse(
            memberId = getCurrentDeviceParameter.memberId,
            device = Device(
                id = currentDevice.id,
                name = currentDevice.name,
                current = currentDevice.current
            )
        )
    }
}
