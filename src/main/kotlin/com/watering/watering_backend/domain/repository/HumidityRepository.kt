package com.watering.watering_backend.domain.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.HumidityHistoryEntity
import com.watering.watering_backend.domain.message.HumidityHistoryMessage

interface HumidityRepository {
    fun saveHistories(histories: List<Pair<DeviceEntity, HumidityHistoryMessage>>): List<HumidityHistoryEntity>
}
