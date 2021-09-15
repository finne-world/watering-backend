package com.watering.watering_backend.infrastructure.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.HumidityHistoryEntity
import com.watering.watering_backend.domain.message.HumidityHistoryMessage
import com.watering.watering_backend.domain.repository.HumidityRepository
import com.watering.watering_backend.infrastructure.table.HumidityHistoryTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Repository

@Repository
class HumidityRepositoryImpl: HumidityRepository {
    override fun saveHistories(histories: List<Pair<DeviceEntity, HumidityHistoryMessage>>): List<HumidityHistoryEntity> {
        HumidityHistoryTable.batchInsert(histories) {
            val deviceEntity: DeviceEntity = it.first
            val message: HumidityHistoryMessage = it.second

            this[HumidityHistoryTable.deviceId] = deviceEntity.id
            this[HumidityHistoryTable.value] = message.value
            this[HumidityHistoryTable.timestamp] = message.timestamp
        }
        .map(HumidityHistoryTable::toEntity)
        .also {
            return it
        }
    }
}
