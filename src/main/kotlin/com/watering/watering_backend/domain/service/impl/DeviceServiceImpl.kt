package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.form.DeviceForm
import com.watering.watering_backend.domain.exception.application.MultipleApplicationException
import com.watering.watering_backend.domain.exception.application.ResourceAlreadyExistsException
import com.watering.watering_backend.domain.exception.application.ResourceCreateFailedException
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.application.ResourceUpdateFailedException
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult
import com.watering.watering_backend.lib.extension.runIfTrue
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository
): DeviceService {
    @Transactional
    override fun getById(id: Long): DeviceEntity {
        this.deviceRepository.getById(id).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "the device with id [${id}] not found."
            )
        }
        .also {
            return it
        }
    }

    @Transactional
    override fun getDevices(userId: Long): List<DeviceEntity> {
        return this.deviceRepository.getDevicesByUserId(userId)
    }

    @Transactional
    override fun registerDevice(userId: Long, name: String): RegisterDeviceResult {
        this.deviceRepository.getDevicesByUserId(userId).any {
            it.name == name
        }.runIfTrue {
            throw ResourceAlreadyExistsException(
                errorDescription = "Already exists device with name [${name}].",
                errorMessage = "Already exists device with name [${name}]. please choice another name."
            )
        }

        this.deviceRepository.create(userId, name).getOrHandle {
            throw ResourceCreateFailedException(
                errorDescription = it.errorDescription,
                errorMessage = "Failed to create device with name [${name}]. please wait a moment and try again."
            )
        }
        .also {
            return RegisterDeviceResult(it.first, it.second)
        }
    }

    @Transactional
    override fun updateDevice(deviceId: Long, deviceForm: DeviceForm): DeviceEntity {
        val device: DeviceEntity = this.deviceRepository.getById(deviceId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "the device with id [${deviceId}] not found."
            )
        }

        deviceForm.getErrors(device).also {
            if (it.isNotEmpty()) {
                throw MultipleApplicationException(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    errors = it
                )
            }
        }

        this.deviceRepository.updateAndGet(deviceId, deviceForm).getOrHandle {
            throw ResourceUpdateFailedException(
                errorDescription = it.errorDescription,
                errorMessage = "failed to update device with id [${deviceId}]. please wait a moment and try again."
            )
        }
        .also {
            return it
        }
    }

    @Transactional
    override fun getCurrentDevice(userId: Long): DeviceEntity {
        this.deviceRepository.getCurrentDevice(userId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "the current device not found. user_id=${userId}.",
                errorMessage = "the current device not configured. please request to [/api/user/${userId}/devices/switch] first."
            )
        }
        .also {
            return it
        }
    }
}
