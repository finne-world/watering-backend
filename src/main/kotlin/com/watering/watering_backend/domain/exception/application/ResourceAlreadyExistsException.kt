package com.watering.watering_backend.domain.exception.application

import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.exception.application.ApplicationException
import org.springframework.http.HttpStatus

class ResourceAlreadyExistsException(
    httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    error: Error = Error.ALREADY_EXISTS_RESOURCE,
    errorDescription: String,
    errorMessage: String = errorDescription
): ApplicationException(
    httpStatus,
    error,
    errorDescription,
    errorMessage
)
