package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.SettingForm
import com.watering.watering_backend.domain.exception.UpdateFailedException
import com.watering.watering_backend.domain.repository.SettingRepository
import com.watering.watering_backend.infrastructure.table.AutomationSettingTable
import com.watering.watering_backend.infrastructure.table.SettingTable
import com.watering.watering_backend.lib.extension.get
import com.watering.watering_backend.lib.extension.ifNegative
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository

@Repository
class SettingRepositoryImpl: SettingRepository {
    override fun getByDeviceId(deviceId: Long): Option<SettingEntity> {
        SettingTable
            .join(
                AutomationSettingTable,
                JoinType.INNER,
                SettingTable.deviceId,
                AutomationSettingTable.deviceId
            ) {
                SettingTable.deviceId eq deviceId
            }
            .selectAll()
            .toList()
            .first()
            .let(SettingTable::toEntity)
            .toOption()
            .also {
                return it
            }
    }

    override fun updateAndGet(
        deviceId: Long,
        settingForm: SettingForm
    ): Either<UpdateFailedException, SettingEntity> {
        SettingTable.update({ SettingTable.deviceId eq deviceId }) {
            settingForm.waterAmount?.also { waterAmount ->
                it[this.waterAmount] = waterAmount
            }
        }
        .ifNegative {
            return UpdateFailedException("failed to update table [${SettingTable.tableName}].").left()
        }

        settingForm.automationSetting?.also { automationSettingForm ->
            AutomationSettingTable.update({ AutomationSettingTable.deviceId eq deviceId }) {
                automationSettingForm.enabled?.also { enabled ->
                    it[this.enabled] = enabled
                }
                automationSettingForm.interval?.also { interval ->
                    it[this.interval] = interval
                }
            }
        }

        return this.getByDeviceId(deviceId).get().right()
    }
}