package com.watering.watering_backend.domain.service.dto.authentication

import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity

data class ApiRegisterUserResult(
    val userEntity: UserEntity,
    val authorities: List<AuthorityEntity>
)
