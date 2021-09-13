package com.watering.watering_backend.domain.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import com.watering.watering_backend.domain.message.WateringHistoryMessage

interface WateringHistoryRepository {
    fun saveHistories(histories: List<Pair<WateringHistoryMessage, DeviceEntity>>): List<WateringHistoryEntity>
}
