package com.watering.watering_backend.domain.exception

class InsertFailedException(
    errorDescription: String
): RuntimeException(
    errorDescription
)
