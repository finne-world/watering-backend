package com.watering.watering_backend.infrastructure.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import com.watering.watering_backend.domain.message.WateringHistoryMessage
import com.watering.watering_backend.domain.repository.WateringHistoryRepository
import com.watering.watering_backend.infrastructure.table.WateringHistoryTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Repository

@Repository
class WateringHistoryRepositoryImpl: WateringHistoryRepository {
    override fun saveHistories(histories: List<Pair<WateringHistoryMessage, DeviceEntity>>): List<WateringHistoryEntity> {
        //TODO: 失敗したレコードとかはどうなるんだ。うまいことMessageからDeviceIdを特定する方法を考えたい。
        WateringHistoryTable.batchInsert(histories) {
            val message: WateringHistoryMessage = it.first
            val deviceEntity: DeviceEntity = it.second

            this[WateringHistoryTable.deviceId] = deviceEntity.id
            this[WateringHistoryTable.amount] = message.amount
            this[WateringHistoryTable.timestamp] = message.timestamp
        }
        .map(WateringHistoryTable::toEntity)
        .also {
            return it
        }
    }
}
