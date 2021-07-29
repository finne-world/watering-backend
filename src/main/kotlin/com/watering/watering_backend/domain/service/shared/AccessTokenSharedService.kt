package com.watering.watering_backend.domain.service.shared

import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.service.shared.dto.access_token.CreateAccessTokenResult
import org.springframework.security.core.userdetails.UserDetails

interface AccessTokenSharedService {
    fun createAccessToken(userDetails: UserDetails): CreateAccessTokenResult

    fun createAccessToken(userEntity: UserEntity): CreateAccessTokenResult
}
