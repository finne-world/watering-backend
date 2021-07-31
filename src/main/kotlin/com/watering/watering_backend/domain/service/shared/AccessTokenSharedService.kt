package com.watering.watering_backend.domain.service.shared

import arrow.core.Either
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails

interface AccessTokenSharedService {
    fun createAccessToken(userDetails: UserDetails): AccessTokenEntity

    fun createAccessToken(userEntity: UserEntity): AccessTokenEntity

    fun verifyAccessToken(token: String): Either<Throwable, AccessTokenEntity>
}
