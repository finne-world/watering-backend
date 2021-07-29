package com.watering.watering_backend.domain.exception

import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import org.springframework.http.HttpStatus

class RefreshTokenExpiredException(
    val refreshToken: RefreshTokenEntity
): ApplicationException(
    HttpStatus.BAD_REQUEST,
    Error.EXPIRED_REFRESH_TOKEN,
    "Refresh token [${refreshToken.token}] was expired.",
    "Refresh token [${refreshToken.token}] was expired. Please make a new signin request."
)
