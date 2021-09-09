package com.watering.watering_backend.application.controller

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.watering.watering_backend.application.controller.helper.getRequestParamName
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.application.json.response.ErrorResponse
import com.watering.watering_backend.domain.exception.application.MultipleApplicationException
import com.watering.watering_backend.domain.exception.application.RefreshTokenExpiredException
import com.watering.watering_backend.domain.exception.application.ResourceAlreadyExistsException
import com.watering.watering_backend.domain.exception.application.ResourceCreateFailedException
import com.watering.watering_backend.domain.exception.application.ResourceUpdateFailedException
import com.watering.watering_backend.domain.exception.application.UserRegistrationFailedException
import com.watering.watering_backend.lib.extension.getRequestURI
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ControllerExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(
        ApplicationException::class,
        ResourceNotFoundException::class,
        ResourceAlreadyExistsException::class,
        ResourceCreateFailedException::class,
        ResourceUpdateFailedException::class,
        RefreshTokenExpiredException::class,
        UserRegistrationFailedException::class)
    fun handleApplicationException(exception: ApplicationException, request: WebRequest): ResponseEntity<Any> {
        return this.handleException(
            exception,
            exception.httpStatus,
            request,
            ErrorResponse(
                httpStatus = exception.httpStatus,
                errors = mapOf(exception.errorCode to exception.errorMessage),
                path = request.getRequestURI()
            )
        )
    }

    @ExceptionHandler(MultipleApplicationException::class)
    fun handleMultipleApplicationException(exception: MultipleApplicationException, request: WebRequest): ResponseEntity<Any> {
        return this.handleException(
            exception,
            exception.httpStatus,
            request,
            ErrorResponse(
                httpStatus = exception.httpStatus,
                errors = exception.errors.associate { it.errorCode to it.errorMessage },
                path = request.getRequestURI()
            )
        )
    }

    override fun handleHttpMediaTypeNotSupported(
        exception: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = status,
                errors = mapOf(Error.MEDIA_TYPE_NOT_SUPPORTED.code to "Content type [${exception.contentType}] not supported."),
                path = request.getRequestURI()
            ),
            headers,
            status,
            request
        )
    }

    override fun handleHttpMessageNotReadable(
        exception: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val cause: Throwable = exception.cause ?: let {
            return super.handleExceptionInternal(
                exception,
                ErrorResponse.createUnknownErrorResponse(request),
                HttpHeaders(),
                status,
                request
            )
        }

        val errorResponse = when (cause) {
            is InvalidFormatException -> ErrorResponse(
                httpStatus = status,
                errors = cause.path.associate { Error.INVALID_PARAMETER.code to "Invalid parameter [${it.fieldName}]." },
                path = request.getRequestURI()
            )
            is JsonMappingException,
            is JsonParseException -> ErrorResponse(
                httpStatus = status,
                errors = mapOf(Error.INVALID_REQUEST_BODY.code to "Invalid request body."),
                path = request.getRequestURI()
            )
            is MissingKotlinParameterException -> ErrorResponse(
                httpStatus = status,
                errors = cause.path.associate { Error.REQUIRE_PARAMETER.code to "Require parameter [${it.fieldName}]." },
                path = request.getRequestURI()
            )
            else -> ErrorResponse.createUnknownErrorResponse(request)
        }

        return super.handleExceptionInternal(
            exception,
            errorResponse,
            headers,
            status,
            request
        )
    }

    override fun handleNoHandlerFoundException(
        exception: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = status,
                errors = mapOf(Error.ENDPOINT_NOT_FOUND.code to "No routes found. invalid url."),
                path = request.getRequestURI()
            ),
            HttpHeaders(),
            status,
            request
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(exception: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = HttpStatus.BAD_REQUEST,
                errors = mapOf(Error.REQUIRE_PARAMETER.code to "Invalid parameter. `${getRequestParamName(exception.parameter)}`"),
                path = request.getRequestURI()
            ),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        )
    }

    override fun handleMissingServletRequestParameter(
        exception: MissingServletRequestParameterException,
        headers: HttpHeaders,
        httpStatus: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            ErrorResponse(
                httpStatus = httpStatus,
                errors = mapOf(Error.REQUIRE_PARAMETER.code to "Required parameter. `${exception.parameterName}`"),
                path = request.getRequestURI()
            ),
            headers,
            httpStatus,
            request
        )
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
                errors = errorBody,
                path = request.getRequestURI()
            ),
            headers,
            httpStatus,
            request
        )
    }

    private fun handleException(
        exception: Exception,
        httpStatus: HttpStatus,
        request: WebRequest,
        errorResponse: ErrorResponse
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            exception,
            errorResponse,
            HttpHeaders(),
            httpStatus,
            request
        )
    }
}
