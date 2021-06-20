package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.AutoWateringSettingRepository
import com.watering.watering_backend.infrastructure.table.AutoWateringSettingTable
import com.watering.watering_backend.lib.extension.runIfTrue
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class AutoWateringSettingRepositoryImpl(
    private val logger: Logger
): AutoWateringSettingRepository {
    override fun insert(deviceId: UUID): Either<InsertFailedException, AutoWateringSettingEntity> {
        return AutoWateringSettingTable.insert {
            it[this.deviceId] = deviceId
        }
        .resultedValues
        .orEmpty()
        .map(AutoWateringSettingTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `auto_watering_setting`.") }
    }

    override fun getEnabledByDeviceId(deviceId: UUID): Option<Boolean> {
        return AutoWateringSettingTable.slice(AutoWateringSettingTable.enabled)
                                       .select { AutoWateringSettingTable.deviceId eq deviceId }
                                       .map { it[AutoWateringSettingTable.enabled] }
                                       .firstOrNone()
    }

    override fun update(deviceId: UUID, enabled: Boolean?, interval: Int?) {
        AutoWateringSettingTable.update({ AutoWateringSettingTable.deviceId eq deviceId }) {
            enabled?.also { enabled ->
                it[AutoWateringSettingTable.enabled] = enabled
            }
            interval?.also { interval ->
                it[AutoWateringSettingTable.interval] = interval
            }
            listOf(enabled, interval).contains(null).runIfTrue {
                it[updatedAt] = LocalDateTime.now()
            }
        }
    }
}
