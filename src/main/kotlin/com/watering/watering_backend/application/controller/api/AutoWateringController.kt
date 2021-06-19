package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.domain.entity.WateringSettingEntity
import com.watering.watering_backend.application.json.parameter.AutoWateringParameter
import com.watering.watering_backend.application.repository.WateringSettingRepository
import com.watering.watering_backend.application.json.response.AutoWateringResponse
import com.watering.watering_backend.application.json.response.`object`.UpdatedValue
import com.watering.watering_backend.lib.extension.getOrThrow
import org.slf4j.Logger
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@RestController
@RequestMapping("/api/auto_watering")
class AutoWateringController(
    private val logger: Logger,
    private val wateringSettingRepository: WateringSettingRepository
) {
    @PutMapping
    fun enabled(@ModelAttribute autoWateringParameter: AutoWateringParameter): AutoWateringResponse {
        val deviceId: UUID = autoWateringParameter.deviceId
        val wateringSetting: WateringSettingEntity = this.wateringSettingRepository.getByDeviceId(deviceId).getOrThrow()

        val newEnabledValue: Boolean = autoWateringParameter.enabled
        val oldEnabledValue: Boolean = wateringSetting.autoWatering

        this.wateringSettingRepository.update(deviceId, autoWatering = newEnabledValue)

        return AutoWateringResponse(
            deviceId = deviceId,
            enabled = UpdatedValue(
                old = oldEnabledValue,
                new = newEnabledValue
            )
        )
    }
}
