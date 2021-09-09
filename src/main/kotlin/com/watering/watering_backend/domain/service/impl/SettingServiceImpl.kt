package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.watering.watering_backend.domain.entity.SettingEntity
import com.watering.watering_backend.domain.entity.form.SettingForm
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.application.ResourceUpdateFailedException
import com.watering.watering_backend.domain.repository.SettingRepository
import com.watering.watering_backend.domain.service.SettingService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SettingServiceImpl(
    private val settingRepository: SettingRepository
): SettingService {
    @Transactional
    override fun getByDeviceId(deviceId: Long): SettingEntity {
        this.settingRepository.getByDeviceId(deviceId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "the setting of device with id [${deviceId}] not found."
            )
        }
        .also {
            return it
        }
    }

    @Transactional
    override fun update(
        deviceId: Long,
        settingForm: SettingForm
    ): SettingEntity {
        val settingEntity: SettingEntity = this.settingRepository.getByDeviceId(deviceId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "the setting of device with id [${deviceId}] not found."
            )
        }

        this.settingRepository.updateAndGet(deviceId, settingForm).getOrHandle {
            throw ResourceUpdateFailedException(
                errorDescription = it.errorDescription,
                errorMessage = "failed to update setting with device_id [${deviceId}]. please wait a moment and try again."
            )
        }
        .also {
            return it
        }
    }
}
