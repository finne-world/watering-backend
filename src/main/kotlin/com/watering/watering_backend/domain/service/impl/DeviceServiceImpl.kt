package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import com.watering.watering_backend.domain.entity.DeviceEntity
import com.watering.watering_backend.domain.entity.WateringSettingEntity
import com.watering.watering_backend.domain.exception.ResourceAlreadyExistsException
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.AutoWateringSettingRepository
import com.watering.watering_backend.domain.repository.DeviceRepository
import com.watering.watering_backend.domain.repository.MemberDeviceMapRepository
import com.watering.watering_backend.domain.repository.WateringSettingRepository
import com.watering.watering_backend.domain.service.DeviceService
import com.watering.watering_backend.domain.service.dto.device.GetCurrentDeviceResult
import com.watering.watering_backend.domain.service.dto.device.GetDevicesResult
import com.watering.watering_backend.domain.service.dto.device.RegisterDeviceResult
import com.watering.watering_backend.lib.extension.getOrThrow
import com.watering.watering_backend.lib.extension.runIfTrue
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

@Service
class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository,
    private val memberDeviceMapRepository: MemberDeviceMapRepository,
    private val wateringSettingRepository: WateringSettingRepository,
    private val autoWateringSettingRepository: AutoWateringSettingRepository
): DeviceService {
    override fun getDevices(memberId: Long): GetDevicesResult = transaction {
        val devices: List<DeviceEntity> = deviceRepository.getDevicesByMemberId(memberId)

        GetDevicesResult(
            devices = devices
        )
    }

    override fun registerDevice(memberId: Long, name: String): RegisterDeviceResult = transaction {
        deviceRepository.getDevicesByMemberId(memberId).any {
            it.name == name
        }.runIfTrue {
            throw ResourceAlreadyExistsException(
                errorDescription = "Already exists device. device_name=${name}."
            )
        }

        val createdDevice: DeviceEntity = deviceRepository.insert(name).getOrThrow().also {
            memberDeviceMapRepository.insert(memberId, it.id).getOrThrow()
        }
        val createdWateringSetting: WateringSettingEntity = wateringSettingRepository.insert(createdDevice.id).getOrThrow()
        val createdAutoWateringSetting: AutoWateringSettingEntity = autoWateringSettingRepository.insert(createdDevice.id).getOrThrow()

        RegisterDeviceResult(
            createdDevice = createdDevice,
            createdWateringSetting = createdWateringSetting,
            createdAutoWateringSetting = createdAutoWateringSetting
        )
    }

    override fun getCurrentDevice(memberId: Long): GetCurrentDeviceResult = transaction {
        val currentDevice: DeviceEntity = deviceRepository.getCurrentDevice(memberId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "current device not found. member_id=${memberId}.",
                errorMessage = "the current device not configured. please request to [/api/devices/switch] first."
            )
        }

        GetCurrentDeviceResult(
            device = currentDevice
        )
    }
}
