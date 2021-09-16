package com.watering.watering_backend.application.json.param

import java.beans.ConstructorProperties
import java.util.UUID

data class GetDevicesFilterParams
@ConstructorProperties(
    "id",
    "serial",
    "name"
)
constructor(
    val id: Long?,
    val serial: UUID?,
    val name: String?
)
