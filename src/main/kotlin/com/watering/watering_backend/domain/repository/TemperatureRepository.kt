package com.watering.watering_backend.domain.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import com.watering.watering_backend.domain.message.TemperatureHistoryMessage

interface TemperatureRepository {
    fun saveHistories(histories: List<Pair<DeviceEntity, TemperatureHistoryMessage>>): List<TemperatureHistoryEntity>

    fun getHistories(deviceId: Long, limit: Int): List<TemperatureHistoryEntity>
}
