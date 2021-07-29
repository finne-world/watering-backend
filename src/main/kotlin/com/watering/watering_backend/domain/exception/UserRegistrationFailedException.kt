package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

class UserRegistrationFailedException(errorMessage: String): ApplicationException(HttpStatus.BAD_REQUEST, Error.FAILED_TO_REGISTER_USER, errorMessage)
