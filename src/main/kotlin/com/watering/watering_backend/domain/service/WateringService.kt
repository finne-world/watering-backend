package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.WateringHistoryEntity

interface WateringService {
    fun receiveHistoryMessages()

    fun getHistories(deviceId: Long, limit: Int): List<WateringHistoryEntity>
}
