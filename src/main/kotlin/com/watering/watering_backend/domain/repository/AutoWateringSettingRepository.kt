package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import java.util.UUID

interface AutoWateringSettingRepository {
    fun insert(deviceId: UUID): Either<InsertFailedException, AutoWateringSettingEntity>

    fun getEnabledByDeviceId(deviceId: UUID): Option<Boolean>

    fun update(deviceId: UUID, enabled: Boolean? = null, interval: Int? = null)
}
