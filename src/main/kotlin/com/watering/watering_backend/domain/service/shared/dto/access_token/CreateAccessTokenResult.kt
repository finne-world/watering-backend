package com.watering.watering_backend.domain.service.shared.dto.access_token

import com.watering.watering_backend.domain.entity.AccessTokenEntity

data class CreateAccessTokenResult(
    val accessToken: AccessTokenEntity
)
