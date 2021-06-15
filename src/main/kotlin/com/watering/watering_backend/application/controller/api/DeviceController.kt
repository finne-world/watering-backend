package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.domain.entity.DeviceEntity
import com.watering.watering_backend.application.domain.entity.WateringSettingEntity
import com.watering.watering_backend.application.exception.DeviceAlreadyExistsException
import com.watering.watering_backend.application.json.parameter.DevicesAddParameter
import com.watering.watering_backend.application.json.response.DevicesAddResponse
import com.watering.watering_backend.application.json.response.`object`.Setting
import com.watering.watering_backend.application.repository.DeviceRepository
import com.watering.watering_backend.application.repository.MemberDeviceMapRepository
import com.watering.watering_backend.application.repository.WateringSettingRepository
import com.watering.watering_backend.lib.extension.getOrThrow
import com.watering.watering_backend.lib.extension.runIfTrue
import org.slf4j.Logger
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val logger: Logger,
    private val deviceRepository: DeviceRepository,
    private val memberDeviceMapRepository: MemberDeviceMapRepository,
    private val wateringSettingRepository: WateringSettingRepository
) {
    @PostMapping("add")
    fun register(@ModelAttribute devicesAddParameter: DevicesAddParameter): DevicesAddResponse {
        this.deviceRepository.getByMemberId(devicesAddParameter.memberId).any {
            it.name == devicesAddParameter.deviceName
        }.runIfTrue {
            throw DeviceAlreadyExistsException("Already exists device. device_name=${devicesAddParameter.deviceName}.")
        }

        val createdDevice: DeviceEntity = this.deviceRepository.insert(devicesAddParameter.deviceName).getOrThrow().also {
            this.memberDeviceMapRepository.insert(devicesAddParameter.memberId, it.id)
        }
        val createdSetting: WateringSettingEntity = this.wateringSettingRepository.insert(createdDevice.id).getOrThrow()

        return DevicesAddResponse(
            deviceId = createdDevice.id,
            setting = Setting(
                autoWatering = createdSetting.autoWatering,
                interval = createdSetting.interval ?: 0
            )
        )
    }
}