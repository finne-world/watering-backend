package com.watering.watering_backend.application.exception

import com.watering.watering_backend.application.constant.Error
import org.springframework.http.HttpStatus

class InsertFailedException(errorMessage: String): ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, Error.FAILED_TO_CREATE_RESOURCE, errorMessage)
