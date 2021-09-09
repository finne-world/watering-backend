package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.exception.application.ApplicationException
import org.springframework.http.HttpStatus

class UnmodifiedAttributeException(
    httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    error: Error = Error.FAILED_TO_UPDATE_RESOURCE,
    attributeName: String,
    errorDescription: String = "the $attributeName cannot be modified.",
    errorMessage: String = errorDescription
): ApplicationException(
    httpStatus,
    error,
    errorDescription,
    errorMessage
)
