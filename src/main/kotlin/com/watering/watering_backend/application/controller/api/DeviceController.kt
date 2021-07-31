package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.controller.helper.convertTo
import com.watering.watering_backend.application.json.parameter.device.RegisterDeviceParameter
import com.watering.watering_backend.application.json.parameter.device.GetCurrentDeviceParameter
import com.watering.watering_backend.application.json.response.device.RegisterDeviceResponse
import com.watering.watering_backend.application.json.response.`object`.AutoWateringSetting
import com.watering.watering_backend.application.json.response.`object`.Device
import com.watering.watering_backend.application.json.response.`object`.WateringSetting
import com.watering.watering_backend.application.json.response.device.GetCurrentDeviceResponse
import com.watering.watering_backend.application.json.response.device.GetDevicesResponse
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val logger: Logger,
    private val deviceService: DeviceService
) {
    @GetMapping
    fun getDevices(@RequestParam("member_id") memberId: Long): GetDevicesResponse {
        val (devices: List<DeviceEntity>) = deviceService.getDevices(memberId)

        return GetDevicesResponse(
            memberId = memberId,
            devices = devices.map(::convertTo)
        )
    }

    @PostMapping
    fun register(@RequestBody registerDeviceParameter: RegisterDeviceParameter): RegisterDeviceResponse {
        val (createdDevice: DeviceEntity,
             createdWateringSetting: WateringSettingEntity,
             createdAutoWateringSetting: AutoWateringSettingEntity
        ) = this.deviceService.registerDevice(
            registerDeviceParameter.memberId,
            registerDeviceParameter.deviceName
        )

        return RegisterDeviceResponse(
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
    fun getCurrentDevice(@RequestBody getCurrentDeviceParameter: GetCurrentDeviceParameter): GetCurrentDeviceResponse {
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
