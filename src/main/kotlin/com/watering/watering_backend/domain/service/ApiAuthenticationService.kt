package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.service.dto.authentication.ApiAuthenticateResult
import com.watering.watering_backend.domain.service.dto.authentication.ApiRegisterUserResult
import com.watering.watering_backend.domain.service.dto.authentication.RefreshAccessTokenResult
import java.util.UUID

interface ApiAuthenticationService {
    fun authenticate(username: String, password: String): ApiAuthenticateResult

    fun registerUser(username: String, password: String, authorities: List<String>): ApiRegisterUserResult

    fun refreshAccessToken(tokenUUID: UUID): RefreshAccessTokenResult
}
