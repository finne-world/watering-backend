package com.watering.watering_backend.application.json.response

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.watering.watering_backend.application.json.response.`object`.Error
import org.springframework.http.HttpStatus

@JsonPropertyOrder("status_code", "timestamp", "errors")
class ErrorResponse(
    httpStatus: HttpStatus,
    errors: Map<Int, String>,
): JsonResponse(httpStatus.value()) {
    val errors: List<Error> = errors.map { Error(it.key, it.value) }
}
