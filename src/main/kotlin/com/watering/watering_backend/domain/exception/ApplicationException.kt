package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

open class ApplicationException(
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    val errorCode: Int,
    val errorMessage: String
): RuntimeException(errorMessage) {
    constructor(
        httpStatus: HttpStatus,
        error: Error,
        errorMessage: String
    ) : this(httpStatus, error.code, errorMessage)
}
