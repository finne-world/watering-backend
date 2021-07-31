package com.watering.watering_backend.domain.exception.authentication

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException

class AuthenticationFailedException(
    val httpStatus: HttpStatus = HttpStatus.UNAUTHORIZED,
    val error: Error,
    errorDescription: String,
    val errorMessage: String = errorDescription,
): AuthenticationException(
    errorDescription
)
