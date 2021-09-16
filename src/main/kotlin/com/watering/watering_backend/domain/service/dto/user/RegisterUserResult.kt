package com.watering.watering_backend.domain.service.dto.user

import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity

data class RegisterUserResult(
    val userEntity: UserEntity,
    val authorities: List<AuthorityEntity>
)
