package com.watering.watering_backend.application.repository

import arrow.core.Either
import arrow.core.firstOrNone
import com.watering.watering_backend.application.domain.entity.MemberDeviceMapEntity
import com.watering.watering_backend.application.exception.InsertFailedException
import com.watering.watering_backend.application.infrastructure.table.MemberDeviceMapTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MemberDeviceMapRepository(
    private val logger: Logger
) {
    fun insert(memberId: Long, deviceId: UUID): Either<InsertFailedException, MemberDeviceMapEntity> = transaction {
        MemberDeviceMapTable.insert {
            it[this.memberId] = memberId
            it[this.deviceId] = deviceId
        }
        .resultedValues
        .orEmpty()
        .map(MemberDeviceMapTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `member_device_map`.") }
    }
}