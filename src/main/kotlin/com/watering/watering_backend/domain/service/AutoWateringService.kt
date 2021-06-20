package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.auto_watering.ChangeEnabledResult
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

interface AutoWateringService {
    fun changeEnabled(deviceId: UUID, newEnabledValue: Boolean): ChangeEnabledResult
}