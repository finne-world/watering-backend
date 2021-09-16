package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.HumidityHistoryEntity

interface HumidityService {
    fun receiveHistoryMessages()

    fun getHistories(deviceId: Long, limit: Int): List<HumidityHistoryEntity>
}
