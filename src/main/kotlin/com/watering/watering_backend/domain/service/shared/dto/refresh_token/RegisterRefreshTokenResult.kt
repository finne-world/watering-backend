package com.watering.watering_backend.domain.service.shared.dto.refresh_token

import com.watering.watering_backend.domain.entity.RefreshTokenEntity

data class RegisterRefreshTokenResult(
    val refreshToken: RefreshTokenEntity
)
