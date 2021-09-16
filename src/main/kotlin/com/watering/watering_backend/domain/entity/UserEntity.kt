package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class UserEntity(
    val id: Long,
    val username: String,
    val password: String,
    val authorities: List<AuthorityEntity>,
    val discordId: Long? = null,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)
