package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.AutoWateringSettingRepository
import com.watering.watering_backend.domain.service.AutoWateringService
import com.watering.watering_backend.domain.service.dto.auto_watering.ChangeEnabledResult
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AutoWateringServiceImpl(
    private val autoWateringSettingRepository: AutoWateringSettingRepository
): AutoWateringService {
    override fun changeEnabled(deviceId: UUID, newEnabledValue: Boolean): ChangeEnabledResult = transaction {
        val oldEnabledValue: Boolean = autoWateringSettingRepository.getEnabledByDeviceId(deviceId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "auto_watering_setting resource not found. device_id=${deviceId}.",
                errorMessage = "the auto watering setting is not found. device_id [${deviceId}]"
            )
        }

        autoWateringSettingRepository.update(deviceId, enabled = newEnabledValue)

        ChangeEnabledResult(
            deviceId = deviceId,
            newEnabledValue = newEnabledValue,
            oldEnabledValue = oldEnabledValue
        )
    }
}
