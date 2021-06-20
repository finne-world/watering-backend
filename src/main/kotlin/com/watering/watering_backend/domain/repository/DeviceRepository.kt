package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.util.UUID

interface DeviceRepository {
    fun insert(name: String): Either<InsertFailedException, DeviceEntity>

    fun getById(id: UUID): Option<DeviceEntity>

    fun getDevicesByMemberId(memberId: Long): List<DeviceEntity>
}
