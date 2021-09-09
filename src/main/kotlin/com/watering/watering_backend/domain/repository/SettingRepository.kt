package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.SettingForm
import com.watering.watering_backend.domain.exception.UpdateFailedException

interface SettingRepository {
    fun getByDeviceId(deviceId: Long): Option<SettingEntity>

    fun updateAndGet(deviceId: Long, settingForm: SettingForm): Either<UpdateFailedException, SettingEntity>
}
