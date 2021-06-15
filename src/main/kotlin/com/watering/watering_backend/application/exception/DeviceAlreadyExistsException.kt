package com.watering.watering_backend.application.exception

import com.watering.watering_backend.application.constant.Error
import org.springframework.http.HttpStatus

class DeviceAlreadyExistsException(errorMessage: String): ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, Error.ALREADY_EXISTS_DEVICE_RESOURCE, errorMessage)
