package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.infrastructure.table.DeviceTable
import com.watering.watering_backend.infrastructure.table.MemberDeviceMapTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DeviceRepositoryImpl(
    private val logger: Logger,
): DeviceRepository {
    override fun insert(name: String): Either<InsertFailedException, DeviceEntity> {
        return DeviceTable.insert {
            it[this.id] = UUID.randomUUID()
            it[this.name] = name
        }
        .resultedValues
        .orEmpty()
        .map(DeviceTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `device`.") }
    }

    override fun getById(id: UUID): Option<DeviceEntity> {
        return DeviceTable.select { DeviceTable.id eq id }.map(DeviceTable::toEntity).firstOrNone()
    }

    override fun getDevicesByMemberId(memberId: Long): List<DeviceEntity> {
        return (DeviceTable innerJoin MemberDeviceMapTable).select {
            MemberDeviceMapTable.memberId eq memberId
        }
        .map(DeviceTable::toEntity)
    }
}
