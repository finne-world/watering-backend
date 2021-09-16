package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.controller.helper.convertTo
import com.watering.watering_backend.application.json.response.history.TemperatureHistoryResponse
import com.watering.watering_backend.application.json.response.history.WateringHistoryResponse
import com.watering.watering_backend.domain.annotation.aspect.CombinationOfUserAndDevice
import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import com.watering.watering_backend.domain.service.TemperatureService
import com.watering.watering_backend.domain.service.WateringService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//TODO: うまいURL設計が思いつかない
@RestController
@RequestMapping("/api/users/{user_id}/devices/{device_id}/histories")
@CombinationOfUserAndDevice
class HistoryController(
    private val wateringService: WateringService,
    private val temperatureService: TemperatureService
) {
    @GetMapping("watering")
    fun getWateringHistories(
        @PathVariable("user_id") userId: Long,
        @PathVariable("device_id") deviceId: Long,
        @RequestParam(name = "limit", required = false) limit: Int = 15
    ): WateringHistoryResponse {
        val wateringHistories: List<WateringHistoryEntity> = this.wateringService.getHistories(deviceId, limit)

        return WateringHistoryResponse(
            deviceId = deviceId,
            histories = wateringHistories.map(::convertTo)
        )
    }

    @GetMapping("temperature")
    fun getTemperatureHistories(
        @PathVariable("user_id") userId: Long,
        @PathVariable("device_id") deviceId: Long,
        @RequestParam(name = "limit", required = false) limit: Int = 15
    ): TemperatureHistoryResponse {
        val temperatureHistories: List<TemperatureHistoryEntity> = this.temperatureService.getHistories(deviceId, limit)

        return TemperatureHistoryResponse(
            deviceId = deviceId,
            histories = temperatureHistories.map(::convertTo)
        )
    }
}
