package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.controller.helper.convertTo
import com.watering.watering_backend.application.json.response.setting.GetSettingResponse
import com.watering.watering_backend.application.json.response.setting.UpdateSettingResponse
import com.watering.watering_backend.domain.annotation.aspect.CombinationOfUserAndDevice
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.SettingForm
import com.watering.watering_backend.domain.service.SettingService
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/users/{user_id}/devices/{device_id}/setting")
@CombinationOfUserAndDevice
class SettingController(
    private val logger: Logger,
    private val settingService: SettingService
) {
    @GetMapping
    fun get(
        @PathVariable("user_id") userId: Long,
        @PathVariable("device_id") deviceId: Long
    ): GetSettingResponse {
        val settingEntity: SettingEntity = this.settingService.getByDeviceId(deviceId)

        return GetSettingResponse(
            deviceId = deviceId,
            setting = convertTo(settingEntity)
        )
    }

    @PutMapping
    fun update(
        @PathVariable("user_id") userId: Long,
        @PathVariable("device_id") deviceId: Long,
        @RequestBody settingForm: SettingForm
    ): UpdateSettingResponse {
        val updatedSettingEntity: SettingEntity = this.settingService.update(deviceId, settingForm)

        return UpdateSettingResponse(
            deviceId = deviceId,
            setting = convertTo(updatedSettingEntity)
        )
    }
}
