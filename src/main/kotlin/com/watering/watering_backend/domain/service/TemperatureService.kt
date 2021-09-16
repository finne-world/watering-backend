package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity

interface TemperatureService {
    fun receiveHistoryMessages()

    fun getHistories(deviceId: Long, limit: Int): List<TemperatureHistoryEntity>
}
