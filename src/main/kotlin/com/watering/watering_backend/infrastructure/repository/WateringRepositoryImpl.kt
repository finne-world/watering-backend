package com.watering.watering_backend.infrastructure.repository

import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import com.watering.watering_backend.domain.message.WateringHistoryMessage
import com.watering.watering_backend.domain.repository.WateringRepository
import com.watering.watering_backend.infrastructure.table.WateringHistoryTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Repository

@Repository
class WateringRepositoryImpl: WateringRepository {
    override fun saveHistories(histories: List<Pair<DeviceEntity, WateringHistoryMessage>>): List<WateringHistoryEntity> {
        //TODO: 失敗したレコードとかはどうなるんだ。うまいことMessageからDeviceIdを特定する方法を考えたい。
        WateringHistoryTable.batchInsert(histories) {
            val deviceEntity: DeviceEntity = it.first
            val message: WateringHistoryMessage = it.second

            this[WateringHistoryTable.deviceId] = deviceEntity.id
            this[WateringHistoryTable.amount] = message.value
            this[WateringHistoryTable.timestamp] = message.timestamp
        }
        .map(WateringHistoryTable::toEntity)
        .also {
            return it
        }
    }
}
