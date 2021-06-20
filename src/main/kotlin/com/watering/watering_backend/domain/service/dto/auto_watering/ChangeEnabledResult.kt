package com.watering.watering_backend.domain.service.dto.auto_watering

import java.util.UUID

data class ChangeEnabledResult(
    val deviceId: UUID,
    val oldEnabledValue: Boolean,
    val newEnabledValue: Boolean
)
