package com.watering.watering_backend.application.json.parameter.auto_watering

import com.watering.watering_backend.application.json.parameter.DeviceBaseParameter
import java.beans.ConstructorProperties
import java.util.UUID

class ChangeEnabledParameter @ConstructorProperties("device_id", "enabled") constructor (
    deviceId: UUID,
    val enabled: Boolean
): DeviceBaseParameter(deviceId)
