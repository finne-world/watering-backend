package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.controller.helper.convertTo
import com.watering.watering_backend.application.controller.helper.convertToForm
import com.watering.watering_backend.application.json.parameter.device.RegisterDeviceRequest
import com.watering.watering_backend.application.json.parameter.device.UpdateDeviceRequest
import com.watering.watering_backend.application.json.response.device.RegisterDeviceResponse
import com.watering.watering_backend.application.json.response.device.GetCurrentDeviceResponse
import com.watering.watering_backend.application.json.response.device.GetDevicesResponse
import com.watering.watering_backend.application.json.response.device.UpdateDeviceResponse
import com.watering.watering_backend.domain.annotation.aspect.CombinationOfUserAndDevice
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.SettingEntity
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//TODO: とりあえずバージョニングはしない
@RestController
@RequestMapping("/api/users/{user_id}/devices")
class DeviceController(
    private val logger: Logger,
    private val deviceService: DeviceService
) {
    @GetMapping
    fun get(
        @PathVariable("user_id") userId: Long,
    ): GetDevicesResponse {
        val devices: List<DeviceEntity> = this.deviceService.getDevices(userId)

        return GetDevicesResponse(
            userId = userId,
            devices = devices.map(::convertTo)
        )
    }

    @PostMapping
    fun create(
        @PathVariable("user_id") userId: Long,
        @RequestBody registerDeviceRequest: RegisterDeviceRequest
    ): RegisterDeviceResponse {
        val (createdDevice: DeviceEntity,
             createdSetting: SettingEntity
        ) = this.deviceService.registerDevice(
            userId,
            registerDeviceRequest.deviceName
        )

        return RegisterDeviceResponse(
            id = createdDevice.id,
            name = createdDevice.name,
            uuid = createdDevice.uuid,
            setting = convertTo(createdSetting)
        )
    }

    @PutMapping("{device_id}")
    @CombinationOfUserAndDevice
    fun update(
        @PathVariable("user_id") userId: Long,
        @PathVariable("device_id") deviceId: Long,
        @RequestBody updateDeviceRequest: UpdateDeviceRequest
    ): UpdateDeviceResponse {
        val updatedDevice: DeviceEntity = this.deviceService.updateDevice(
            deviceId,
            updateDeviceRequest.convertToForm()
        )

        return UpdateDeviceResponse(
            device = convertTo(updatedDevice)
        )
    }

    @GetMapping("current")
    fun getCurrentDevice(
        @PathVariable("user_id") userId: Long
    ): GetCurrentDeviceResponse {
        val currentDevice: DeviceEntity = this.deviceService.getCurrentDevice(userId)

        return GetCurrentDeviceResponse(
            userId = currentDevice.userId,
            device = convertTo(currentDevice)
        )
    }
}
