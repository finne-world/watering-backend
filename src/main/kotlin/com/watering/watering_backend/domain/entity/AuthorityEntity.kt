package com.watering.watering_backend.domain.entity

import java.time.LocalDateTime

class AuthorityEntity(
    val id: Long,
    val name: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime
): TableBasedEntity(createdAt, updatedAt)
