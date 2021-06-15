package com.watering.watering_backend.application.exception

import com.watering.watering_backend.application.constant.Error
import org.springframework.http.HttpStatus
import java.util.UUID

class DeviceNotFoundException(deviceId: UUID): ApplicationException(HttpStatus.NOT_FOUND, Error.DEVICE_NOT_FOUND, "Device not found. device_id=${deviceId}.")
