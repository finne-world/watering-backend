package com.watering.watering_backend.application.controller

import com.watering.watering_backend.application.constant.Error
import com.watering.watering_backend.application.exception.ApplicationException
import com.watering.watering_backend.application.exception.DeviceNotFoundException
import com.watering.watering_backend.application.exception.InsertFailedException
import com.watering.watering_backend.application.json.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ControllerExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(exception: ApplicationException, request: WebRequest): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = exception.httpStatus,
                errors = mapOf(exception.errorCode to exception.errorMessage)
            ),
            HttpHeaders(),
            exception.httpStatus,
            request
        )
    }

    @ExceptionHandler(DeviceNotFoundException::class)
    fun handleDeviceNotFoundException(exception: DeviceNotFoundException, request: WebRequest): ResponseEntity<Any> {
        return handleApplicationException(exception, request)
    }

    @ExceptionHandler(InsertFailedException::class)
    fun handleInsertFailedException(exception: InsertFailedException, request: WebRequest): ResponseEntity<Any> {
        return handleApplicationException(exception, request)
    }

    override fun handleBindException(
        exception: BindException,
        headers: HttpHeaders,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: List<ObjectError> = exception.allErrors

        val errorBody: Map<Int, String> = errors.associate {
            when (it) {
                is FieldError -> {
                    val isNothingParameter: Boolean = it.rejectedValue == null
                    val errorCode: Int = if (isNothingParameter) Error.REQUIRE_PARAMETER.code else Error.INVALID_PARAMETER.code
                    val errorMessage = if (isNothingParameter) "Required parameter. `${it.field}`" else "Invalid parameter. `${it.field}`"

                    errorCode to errorMessage
                }
                else -> {
                    Error.UNKNOWN_ERROR.code to it.defaultMessage!!
                }
            }
        }

        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = httpStatus,
                errors = errorBody
            ),
            HttpHeaders(),
            httpStatus,
            request
        )
    }
}
