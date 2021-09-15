package com.watering.watering_backend.infrastructure.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import com.watering.watering_backend.domain.message.TemperatureHistoryMessage
import com.watering.watering_backend.domain.repository.TemperatureRepository
import com.watering.watering_backend.infrastructure.table.TemperatureHistoryTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Repository

@Repository
class TemperatureRepositoryImpl: TemperatureRepository {
    override fun saveHistories(histories: List<Pair<DeviceEntity, TemperatureHistoryMessage>>): List<TemperatureHistoryEntity> {
        //TODO: 失敗したレコードとかはどうなるんだ。うまいことMessageからDeviceIdを特定する方法を考えたい。
        TemperatureHistoryTable.batchInsert(histories) {
            val deviceEntity: DeviceEntity = it.first
            val message: TemperatureHistoryMessage = it.second

            this[TemperatureHistoryTable.deviceId] = deviceEntity.id
            this[TemperatureHistoryTable.value] = message.value
            this[TemperatureHistoryTable.timestamp] = message.timestamp
        }
        .map(TemperatureHistoryTable::toEntity)
        .also {
            return it
        }
    }
}
