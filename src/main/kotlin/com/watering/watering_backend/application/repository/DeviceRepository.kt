package com.watering.watering_backend.application.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import com.watering.watering_backend.application.domain.entity.DeviceEntity
import com.watering.watering_backend.application.domain.entity.MemberDeviceMapEntity
import com.watering.watering_backend.application.exception.InsertFailedException
import com.watering.watering_backend.application.infrastructure.table.DeviceTable
import com.watering.watering_backend.application.infrastructure.table.MemberDeviceMapTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DeviceRepository(
    private val logger: Logger,
) {
    fun insert(name: String): Either<InsertFailedException, DeviceEntity> = transaction {
        DeviceTable.insert {
            it[this.id] = UUID.randomUUID()
            it[this.name] = name
        }
        .resultedValues
        .orEmpty()
        .map(DeviceTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `device`.") }
    }

    fun getById(id: UUID): Option<DeviceEntity> = transaction {
        DeviceTable.select { DeviceTable.id eq id }.map(DeviceTable::toEntity).firstOrNone()
    }

    fun getByMemberId(memberId: Long): List<DeviceEntity> = transaction {
        (DeviceTable innerJoin MemberDeviceMapTable).select {
            MemberDeviceMapTable.memberId eq memberId
        }
        .map(DeviceTable::toEntity)
    }
}