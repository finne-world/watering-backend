package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.response.`object`.User
import com.watering.watering_backend.domain.entity.UserEntity

fun convertTo(entity: UserEntity): User {
    return User(
        entity.id,
        entity.username,
        entity.authorities.map { it.name },
        entity.discordId
    )
}
