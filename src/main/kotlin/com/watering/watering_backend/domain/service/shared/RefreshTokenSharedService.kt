package com.watering.watering_backend.domain.service.shared

import com.watering.watering_backend.domain.service.shared.dto.refresh_token.RegisterRefreshTokenResult
import org.springframework.security.core.userdetails.UserDetails

interface RefreshTokenSharedService {
    fun registerRefreshToken(userDetails: UserDetails): RegisterRefreshTokenResult
}