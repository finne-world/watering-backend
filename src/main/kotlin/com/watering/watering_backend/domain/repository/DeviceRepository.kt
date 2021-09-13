package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.DeviceForm
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.exception.UpdateFailedException
import java.util.UUID

interface DeviceRepository {
    fun create(userId: Long, name: String): Either<InsertFailedException, Pair<DeviceEntity, SettingEntity>>

    fun update(id: Long, deviceForm: DeviceForm): Either<UpdateFailedException, Int>

    fun getById(id: Long): Option<DeviceEntity>

    fun getDevicesByUserId(userId: Long): List<DeviceEntity>

    fun getCurrentDevice(userId: Long): Option<DeviceEntity>

    fun updateAndGet(id: Long, deviceForm: DeviceForm): Either<UpdateFailedException, DeviceEntity>

    fun getDevicesBySerials(serials: List<UUID>): List<DeviceEntity>
}
