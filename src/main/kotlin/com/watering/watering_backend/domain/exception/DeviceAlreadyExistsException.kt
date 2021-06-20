package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

class DeviceAlreadyExistsException(errorMessage: String): ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, Error.ALREADY_EXISTS_DEVICE_RESOURCE, errorMessage)
