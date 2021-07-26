package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.json.parameter.authentication.SigninRequest
import com.watering.watering_backend.application.json.parameter.authentication.SignupRequest
import com.watering.watering_backend.application.json.response.authentication.SigninResponse
import com.watering.watering_backend.application.json.response.authentication.SignupResponse
import com.watering.watering_backend.domain.constant.TokenType
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.service.ApiAuthenticationService
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    private val apiAuthenticationService: ApiAuthenticationService
) {
    @PostMapping("signin")
    fun signin(@ModelAttribute signinRequest: SigninRequest): SigninResponse {
        val (userEntity: UserEntity,
             accessToken: AccessTokenEntity,
             refreshToken: RefreshTokenEntity,
             authorities: List<String>) = this.apiAuthenticationService.authenticate(signinRequest.username, signinRequest.password)

        return SigninResponse(
            id = userEntity.id,
            username = userEntity.username,
            authorities = authorities,
            accessToken = accessToken.token,
            refreshToken = refreshToken.token.toString(),
            expiresIn = accessToken.expiresIn,
            tokenType = TokenType.BEARER.value
        )
    }

    //TODO: 開発用アカウント登録API（リリース時には削除する）
    @PostMapping("signup")
    fun signup(@ModelAttribute signupRequest: SignupRequest): SignupResponse {
        val (userEntity,
             authorities: List<AuthorityEntity>
        ) = this.apiAuthenticationService.registerUser(
            signupRequest.username,
            signupRequest.password,
            signupRequest.authorities
        )

        return SignupResponse(
            id = userEntity.id,
            username = userEntity.username,
            authorities = authorities.map { it.name }
        )
    }
}
