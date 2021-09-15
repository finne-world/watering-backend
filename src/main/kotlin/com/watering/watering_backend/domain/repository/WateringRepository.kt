package com.watering.watering_backend.domain.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import com.watering.watering_backend.domain.message.WateringHistoryMessage

interface WateringRepository {
    fun saveHistories(histories: List<Pair<DeviceEntity, WateringHistoryMessage>>): List<WateringHistoryEntity>

    fun getHistories(deviceId: Long, limit: Int): List<WateringHistoryEntity>
}
