package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

class ResourceCreateFailedException(
    httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    error: Error = Error.FAILED_TO_CREATE_RESOURCE,
    errorDescription: String,
    errorMessage: String = errorDescription
): ApplicationException(
    httpStatus,
    error,
    errorDescription,
    errorMessage
)
