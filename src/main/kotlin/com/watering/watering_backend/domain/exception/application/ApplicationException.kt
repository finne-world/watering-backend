package com.watering.watering_backend.domain.exception.application

import com.watering.watering_backend.domain.constant.Error
import org.springframework.http.HttpStatus

open class ApplicationException private constructor(
    val httpStatus: HttpStatus,
    val errorCode: Int,
    errorDescription: String,
    val errorMessage: String
): RuntimeException(errorDescription) {
    companion object {
        const val UNKNOWN_MESSAGE = "An error has occurred, Please wait a moment and try again"
    }

    constructor(
        httpStatus: HttpStatus,
        error: Error,
        errorDescription: String
    ) : this(httpStatus, error.code, errorDescription, errorDescription)

    constructor(
        httpStatus: HttpStatus,
        error: Error,
        errorDescription: String,
        errorMessage: String
    ): this(httpStatus, error.code, errorDescription, errorMessage)
}
