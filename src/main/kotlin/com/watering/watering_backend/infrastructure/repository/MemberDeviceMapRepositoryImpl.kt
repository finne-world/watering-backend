package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.MemberDeviceMapEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.MemberDeviceMapRepository
import com.watering.watering_backend.infrastructure.table.MemberDeviceMapTable
import org.jetbrains.exposed.sql.insert
import org.slf4j.Logger
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MemberDeviceMapRepositoryImpl(
    private val logger: Logger
): MemberDeviceMapRepository {
    override fun insert(memberId: Long, deviceId: UUID): Either<InsertFailedException, MemberDeviceMapEntity> {
        return MemberDeviceMapTable.insert {
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
