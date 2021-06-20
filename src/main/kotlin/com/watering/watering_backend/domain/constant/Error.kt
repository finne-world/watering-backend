package com.watering.watering_backend.domain.constant

enum class Error(val code: Int) {
    REQUIRE_PARAMETER(1001),
    INVALID_PARAMETER(1002),
    RESOURCE_NOT_FOUND(1003),
    ALREADY_EXISTS_DEVICE_RESOURCE(1004),
    FAILED_TO_CREATE_RESOURCE(1005),
    UNKNOWN_ERROR(1100)
}
