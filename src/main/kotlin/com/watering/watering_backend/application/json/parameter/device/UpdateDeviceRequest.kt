package com.watering.watering_backend.application.json.parameter.device

import java.beans.ConstructorProperties
import java.util.UUID

data class UpdateDeviceRequest
@ConstructorProperties(
    "id",
    "uuid",
    "user_id",
    "name",
    "current"
) constructor (
    val id: Long?,
    val uuid: UUID?,
    val userId: Long?,
    val name: String?,
    val current: Boolean?
)
