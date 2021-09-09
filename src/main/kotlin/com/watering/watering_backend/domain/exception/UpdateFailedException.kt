package com.watering.watering_backend.domain.exception

class UpdateFailedException(
    val errorDescription: String
): RuntimeException(
    errorDescription
)
