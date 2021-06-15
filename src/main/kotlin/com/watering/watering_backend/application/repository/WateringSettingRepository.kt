package com.watering.watering_backend.application.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import com.watering.watering_backend.application.domain.entity.WateringSettingEntity
import com.watering.watering_backend.application.exception.DeviceNotFoundException
import com.watering.watering_backend.application.exception.InsertFailedException
import com.watering.watering_backend.application.infrastructure.table.WateringSettingTable
import com.watering.watering_backend.lib.extension.runIfTrue
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class WateringSettingRepository(
    private val logger: Logger
) {
    fun insert(deviceId: UUID): Either<InsertFailedException, WateringSettingEntity> = transaction {
        WateringSettingTable.insert {
            it[this.deviceId] = deviceId
        }
        .resultedValues
        .orEmpty()
        .map(WateringSettingTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `watering_setting`.") }
    }

    fun getByDeviceId(deviceId: UUID): Either<DeviceNotFoundException, WateringSettingEntity> = transaction {
        WateringSettingTable.select {
            WateringSettingTable.deviceId eq deviceId
        }
        .map(WateringSettingTable::toEntity)
        .firstOrNone()
        .toEither { DeviceNotFoundException(deviceId) }
    }

    fun update(deviceId: UUID, autoWatering: Boolean? = null, interval: Int? = null) = transaction {
        WateringSettingTable.update({ WateringSettingTable.deviceId eq deviceId }) {
            autoWatering?.also {
                autoWatering -> it[this.autoWatering] = autoWatering
            }
            interval?.also {
                interval -> it[this.interval] = interval
            }
            listOf(autoWatering, interval).contains(null).runIfTrue {
                it[this.updatedAt] = LocalDateTime.now()
            }
        }
    }
}