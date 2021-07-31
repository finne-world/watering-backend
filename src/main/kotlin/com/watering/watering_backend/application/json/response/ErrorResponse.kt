package com.watering.watering_backend.application.json.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.`object`.ErrorObject
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.lib.extension.getRequestURI
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest

@JsonPropertyOrder("status_code", "timestamp", "errors", "path")
class ErrorResponse(
    httpStatus: HttpStatus,
    errors: Map<Int, String>,
    val path: String
): JsonResponse(httpStatus.value()) {
    companion object {
        fun createUnknownErrorResponse(request: WebRequest): ErrorResponse {
            return ErrorResponse(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                errors = mapOf(Error.UNKNOWN_ERROR.code to ApplicationException.UNKNOWN_MESSAGE),
                path = request.getRequestURI()
            )
        }
    }
    val errors: List<ErrorObject> = errors.map { ErrorObject(it.key, it.value) }
}
