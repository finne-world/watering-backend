package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.WateringSettingRepository
import com.watering.watering_backend.infrastructure.table.WateringSettingTable
import com.watering.watering_backend.lib.extension.runIfTrue
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class WateringSettingRepositoryImpl(
    private val logger: Logger
): WateringSettingRepository {
    override fun insert(deviceId: UUID): Either<InsertFailedException, WateringSettingEntity> {
        return WateringSettingTable.insert {
            it[this.deviceId] = deviceId
        }
        .resultedValues
        .orEmpty()
        .map(WateringSettingTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `watering_setting`.") }
    }

    override fun getByDeviceId(deviceId: UUID): Either<ResourceNotFoundException, WateringSettingEntity> {
        return WateringSettingTable.select {
            WateringSettingTable.deviceId eq deviceId
        }
        .map(WateringSettingTable::toEntity)
        .firstOrNone()
        .toEither { ResourceNotFoundException("device resource not found. uuid=${deviceId}.") }
    }

    override fun update(deviceId: UUID, waterAmount: Int?) {
        WateringSettingTable.update({ WateringSettingTable.deviceId eq deviceId }) {
            waterAmount?.also { waterAmount ->
                it[this.waterAmount] = waterAmount
            }
            listOf(waterAmount).contains(null).runIfTrue {
                it[this.updatedAt] = LocalDateTime.now()
            }
        }
    }
}
