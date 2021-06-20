package com.watering.watering_backend.domain.repository

import arrow.core.Either
import com.watering.watering_backend.domain.entity.MemberDeviceMapEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.util.UUID

interface MemberDeviceMapRepository {
    fun insert(memberId: Long, deviceId: UUID): Either<InsertFailedException, MemberDeviceMapEntity>
}
