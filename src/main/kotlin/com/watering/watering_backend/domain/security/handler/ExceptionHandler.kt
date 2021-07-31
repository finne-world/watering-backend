package com.watering.watering_backend.domain.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.watering.watering_backend.domain.exception.authentication.AuthenticationFailedException
import com.watering.watering_backend.domain.security.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.web.util.NestedServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandler(
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val UNKNOWN_MESSAGE = "An error has occurred, Please wait a moment and try again"
    }

    fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        throwable: Throwable
    ) {
        val responseObject: ErrorResponse = getErrorResponse(request, throwable)

        response.status = responseObject.statusCode
        response.writer.write(
            objectMapper.writeValueAsString(responseObject)
        )
    }

    private tailrec fun getErrorResponse(request: HttpServletRequest, throwable: Throwable): ErrorResponse {
        return when (throwable) {
            is NestedServletException -> getErrorResponse(request, throwable.rootCause)
            is AuthenticationFailedException -> ErrorResponse(
                statusCode = throwable.httpStatus.value(),
                description = throwable.errorMessage,
                path = request.requestURI
            )
            is BadCredentialsException -> ErrorResponse(
                statusCode = HttpStatus.BAD_REQUEST.value(),
                description = "the username or password is incorrect.",
                path = request.requestURI
            )
            else -> ErrorResponse(
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                description = throwable.message ?: UNKNOWN_MESSAGE,
                path = request.requestURI
            )
        }
    }
}
