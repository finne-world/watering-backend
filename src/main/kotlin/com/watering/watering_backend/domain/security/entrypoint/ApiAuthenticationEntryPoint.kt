package com.watering.watering_backend.domain.security.entrypoint

import com.watering.watering_backend.domain.security.handler.ExceptionHandler
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiAuthenticationEntryPoint(
    private val exceptionHandler: ExceptionHandler
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        this.exceptionHandler.handle(request, response, authException)
    }
}
