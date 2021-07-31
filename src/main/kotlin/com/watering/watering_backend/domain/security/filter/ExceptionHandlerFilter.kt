package com.watering.watering_backend.domain.security.filter

import com.watering.watering_backend.domain.security.handler.ExceptionHandler
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter(
    private val exceptionHandler: ExceptionHandler
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        }
        catch (throwable: Throwable) {
            this.exceptionHandler.handle(request, response, throwable)
        }
    }
}
