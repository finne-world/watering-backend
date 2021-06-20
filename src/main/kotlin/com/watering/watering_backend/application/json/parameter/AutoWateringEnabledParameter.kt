package com.watering.watering_backend.application.json.parameter

import java.beans.ConstructorProperties
import java.util.UUID

class AutoWateringEnabledParameter @ConstructorProperties("device_id", "enabled") constructor (
    deviceId: UUID,
    val enabled: Boolean
): DeviceBaseParameter(deviceId)
