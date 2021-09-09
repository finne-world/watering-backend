package com.watering.watering_backend.domain.exception

class InsertFailedException(
    val errorDescription: String
): RuntimeException(
    errorDescription
)
