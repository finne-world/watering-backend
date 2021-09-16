package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.authentication.ApiAuthenticateResult
import com.watering.watering_backend.domain.service.dto.user.RegisterUserResult
import com.watering.watering_backend.domain.service.dto.authentication.RefreshAccessTokenResult
import java.util.UUID

interface ApiAuthenticationService {
    fun authenticate(username: String, password: String): ApiAuthenticateResult

    fun refreshAccessToken(tokenUUID: UUID): RefreshAccessTokenResult
}
