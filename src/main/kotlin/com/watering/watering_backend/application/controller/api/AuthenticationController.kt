package com.watering.watering_backend.application.controller.api

import com.watering.watering_backend.application.json.parameter.authentication.RefreshTokenRequest
import com.watering.watering_backend.application.json.parameter.authentication.SigninRequest
import com.watering.watering_backend.application.json.parameter.authentication.SignupRequest
import com.watering.watering_backend.application.json.response.authentication.RefreshTokenResponse
import com.watering.watering_backend.application.json.response.authentication.SigninResponse
import com.watering.watering_backend.application.json.response.authentication.SignupResponse
import com.watering.watering_backend.domain.constant.TokenType
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.service.ApiAuthenticationService
import com.watering.watering_backend.domain.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    private val apiAuthenticationService: ApiAuthenticationService,
    private val userService: UserService
) {
    @PostMapping("signin")
    fun signin(@RequestBody signinRequest: SigninRequest): SigninResponse {
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

    //TODO: ??????????????????????????????API???????????????????????????????????????
    @PostMapping("signup")
    fun signup(@RequestBody signupRequest: SignupRequest): SignupResponse {
        val (userEntity,
             authorities: List<AuthorityEntity>
        ) = this.userService.registerUser(
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

    @PostMapping("refresh_token")
    fun refreshToken(@RequestBody @Valid refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        val (accessToken: AccessTokenEntity,
             refreshToken: RefreshTokenEntity,
             tokenType: TokenType) = this.apiAuthenticationService.refreshAccessToken(refreshTokenRequest.refreshToken)

        return RefreshTokenResponse(
            accessToken = accessToken.token,
            expiresIn = accessToken.expiresIn,
            refreshToken = refreshToken.token.toString(),
            tokenType = tokenType.value
        )
    }
}
