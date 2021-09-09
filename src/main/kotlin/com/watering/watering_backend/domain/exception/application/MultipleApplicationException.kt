package com.watering.watering_backend.domain.exception.application

import org.springframework.http.HttpStatus

class MultipleApplicationException(
    val httpStatus: HttpStatus,
    val errors: List<ApplicationException>,
): RuntimeException()
