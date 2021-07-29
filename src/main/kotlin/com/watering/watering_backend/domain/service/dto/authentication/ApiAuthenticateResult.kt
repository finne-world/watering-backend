package com.watering.watering_backend.domain.service.dto.authentication

import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity

data class ApiAuthenticateResult(
    val userEntity: UserEntity,
    val accessToken: AccessTokenEntity,
    val refreshToken: RefreshTokenEntity,
    val authorities: List<String>
)
