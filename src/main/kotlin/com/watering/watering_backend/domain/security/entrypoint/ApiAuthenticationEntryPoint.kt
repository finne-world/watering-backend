package com.watering.watering_backend.domain.security.entrypoint

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.writer.write(
            objectMapper.writeValueAsString(
                AuthenticationErrorResponse(
                    description = authException.message ?: "unauthorized",
                    path = request.requestURI
                )
            )
        )
    }
}

@JsonPropertyOrder("status_code", "timestamp", "description", "path")
data class AuthenticationErrorResponse(
    val statusCode: Int = HttpStatus.UNAUTHORIZED.value(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val description: String,
    val path: String
)
