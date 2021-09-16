package com.watering.watering_backend.domain.entity.filter

import java.util.UUID

data class DeviceFilter(
    val id: Long? = null,
    val serial: UUID? = null,
    val name: String? = null
)
