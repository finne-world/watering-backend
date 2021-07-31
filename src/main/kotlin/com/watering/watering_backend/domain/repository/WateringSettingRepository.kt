package com.watering.watering_backend.domain.repository

import arrow.core.Either
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.util.UUID

interface WateringSettingRepository {
    fun insert(deviceId: UUID): Either<InsertFailedException, WateringSettingEntity>

    fun getByDeviceId(deviceId: UUID): Either<ResourceNotFoundException, WateringSettingEntity>

    fun update(deviceId: UUID, waterAmount: Int? = null)
}
