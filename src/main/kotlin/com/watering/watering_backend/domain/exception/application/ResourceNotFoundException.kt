package com.watering.watering_backend.domain.exception.application

import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.exception.application.ApplicationException
import org.springframework.http.HttpStatus

open class ResourceNotFoundException(
    httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    error: Error = Error.RESOURCE_NOT_FOUND,
    errorDescription: String,
    errorMessage: String = errorDescription,
): ApplicationException(
    httpStatus,
    error,
    errorDescription,
    errorMessage
)
