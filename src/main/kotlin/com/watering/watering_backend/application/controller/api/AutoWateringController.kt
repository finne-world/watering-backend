package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.json.parameter.auto_watering.ChangeEnabledParameter
import com.watering.watering_backend.application.json.response.AutoWateringResponse
import com.watering.watering_backend.application.json.response.`object`.UpdatedValue
import com.watering.watering_backend.domain.service.AutoWateringService
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
    private val autoWateringService: AutoWateringService
) {
    @PutMapping
    fun changeEnabled(@ModelAttribute changeEnabledParameter: ChangeEnabledParameter): AutoWateringResponse {
        val (deviceId: UUID,
             oldEnabledValue: Boolean,
             newEnabledValue: Boolean) = autoWateringService.changeEnabled(changeEnabledParameter.deviceId, changeEnabledParameter.enabled)

        return AutoWateringResponse(
            deviceId = deviceId,
            enabled = UpdatedValue(
                old = oldEnabledValue,
                new = newEnabledValue
            )
        )
    }
}
