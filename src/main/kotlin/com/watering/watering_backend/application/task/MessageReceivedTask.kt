package com.watering.watering_backend.application.task

import com.watering.watering_backend.domain.service.WateringService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MessageReceivedTask(
    private val wateringService: WateringService
) {
    @Scheduled(fixedDelay = 5000)
    fun receiveWateringHistories() {
        this.wateringService.receiveHistoryMessages()
    }
}
