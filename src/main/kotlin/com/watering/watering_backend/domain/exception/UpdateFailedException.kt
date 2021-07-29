package com.watering.watering_backend.domain.exception

class UpdateFailedException(
    errorDescription: String
): RuntimeException(
    errorDescription
)
