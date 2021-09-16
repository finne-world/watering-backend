package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.firstOrNone
import arrow.core.getOrElse
import arrow.core.getOrHandle
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.filter.DeviceFilter
import com.watering.watering_backend.domain.entity.form.DeviceForm
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.exception.UpdateFailedException
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.infrastructure.table.AutomationSettingTable
import com.watering.watering_backend.infrastructure.table.DeviceTable
import com.watering.watering_backend.infrastructure.table.SettingTable
import com.watering.watering_backend.lib.extension.get
import com.watering.watering_backend.lib.extension.ifNegative
import com.watering.watering_backend.lib.extension.insert
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DeviceRepositoryImpl(
    private val logger: Logger,
): DeviceRepository {
    override fun create(userId: Long, name: String): Either<InsertFailedException, Pair<DeviceEntity, SettingEntity>> {
        val createdDevice: DeviceEntity = DeviceTable.insert {
            it[this.serial] = UUID.randomUUID()
            it[this.name] = name
            it[this.userId] = userId
        }
        .getOrElse {
            return InsertFailedException("Failed to create resource [${DeviceTable.tableName}].").left()
        }
        .let(DeviceTable::toEntity)

        val createdSetting: SettingEntity = SettingTable.insert {
            it[this.deviceId] = createdDevice.id
        }
        .getOrElse {
            return InsertFailedException("Failed to create resource [${SettingTable.tableName}].").left()
        }
        .let { settingRow ->
            AutomationSettingTable.insert {
                it[this.deviceId] = createdDevice.id
            }
            .getOrElse {
                return InsertFailedException("Failed to create resource [${AutomationSettingTable.tableName}].").left()
            }
            .let {
                SettingTable.toEntity(settingRow, AutomationSettingTable.toEntity(it))
            }
        }

        return Pair(createdDevice, createdSetting).right()
    }

    override fun update(id: Long, deviceForm: DeviceForm): Either<UpdateFailedException, Int> {
        DeviceTable.update({ DeviceTable.id eq id }) {
            deviceForm.name?.also { name ->
                it[this.name] = name
            }
            deviceForm.current?.also { current ->
                it[this.current] = current
            }
        }
        .ifNegative {
            return UpdateFailedException("Failed to update resource [${DeviceTable.tableName}].").left()
        }.also {
            return it.right()
        }
    }

    override fun getById(id: Long): Option<DeviceEntity> {
        return this.getByCondition { DeviceTable.id eq id }
    }

    private fun getByCondition(where: SqlExpressionBuilder.() -> Op<Boolean>): Option<DeviceEntity> {
        (DeviceTable innerJoin SettingTable innerJoin AutomationSettingTable)
            .select(where)
            .firstOrNone()
            .getOrElse {
                return None
            }
            .let {
                DeviceTable.toEntity(it).toOption()
            }
            .also {
                return it
            }
    }

    override fun getDevicesByUserId(userId: Long, filter: DeviceFilter): List<DeviceEntity> {
        DeviceTable.selectAll().also { query ->
            filter.id?.also {
                query.andWhere { DeviceTable.id eq it }
            }
            filter.serial?.also {
                query.andWhere { DeviceTable.serial eq it }
            }
            filter.name?.also {
                query.andWhere { DeviceTable.name like it }
            }
        }
        .map(DeviceTable::toEntity)
        .also {
            return it
        }
    }

    override fun getCurrentDevice(userId: Long): Option<DeviceEntity> {
        val currentDevices: List<DeviceEntity> = this.getDevicesByUserId(userId).filter { it.current }

        // TODO: 業務ロジック？ Service クラスで処理するべき？
        if (currentDevices.size > 1) {
            throw IllegalStateException("multiple current devices. member_id=${userId}.")
        }

        return currentDevices.firstOrNone()
    }

    override fun updateAndGet(id: Long, deviceForm: DeviceForm): Either<UpdateFailedException, DeviceEntity> {
        this.update(id, deviceForm).getOrHandle {
            return it.left()
        }
        this.getById(id).also {
            return it.get().right()
        }
    }

    override fun getDevicesBySerials(serials: List<UUID>): List<DeviceEntity> {
        DeviceTable.select {
            DeviceTable.serial inList serials
        }
        .map(DeviceTable::toEntity)
        .also {
            return it
        }
    }
}
