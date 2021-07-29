package com.watering.watering_backend.domain.service.dto.authentication

import com.watering.watering_backend.domain.constant.TokenType
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity

data class RefreshAccessTokenResult(
    val accessToken: AccessTokenEntity,
    val refreshToken: RefreshTokenEntity,
    val tokenType: TokenType
)
