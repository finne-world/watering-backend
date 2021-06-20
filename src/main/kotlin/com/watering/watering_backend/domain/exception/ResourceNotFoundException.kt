package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

class ResourceNotFoundException(message: String): ApplicationException(HttpStatus.NOT_FOUND, Error.RESOURCE_NOT_FOUND, message)
