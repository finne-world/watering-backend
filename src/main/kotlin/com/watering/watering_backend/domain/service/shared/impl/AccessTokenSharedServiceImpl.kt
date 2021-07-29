package com.watering.watering_backend.domain.service.shared.impl

import com.auth0.jwt.interfaces.JWTVerifier
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.domain.service.shared.dto.access_token.CreateAccessTokenResult
import com.watering.watering_backend.lib.JWTProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class AccessTokenSharedServiceImpl(
    private val jwtProvider: JWTProvider,
    private val jwtVerifier: JWTVerifier
): AccessTokenSharedService {
    override fun createAccessToken(userDetails: UserDetails): CreateAccessTokenResult {
        return CreateAccessTokenResult(
            this.jwtProvider.createToken(userDetails.username)
        )
    }

    override fun createAccessToken(userEntity: UserEntity): CreateAccessTokenResult {
        return CreateAccessTokenResult(
            this.jwtProvider.createToken(userEntity.username)
        )
    }
}