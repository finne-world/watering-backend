package com.watering.watering_backend.lib

import arrow.core.None
import arrow.core.Option
import arrow.core.toOption
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

@Component
class BearerTokenResolver {
    companion object {
        const val MATCHER_TOKEN_NAME: String = "token"
        const val AUTHORIZATION_PATTERN: String = "^Bearer (?<${MATCHER_TOKEN_NAME}>[a-zA-Z0-9-._~+/]+=*)$"
    }

    fun resolve(httpServletRequest: HttpServletRequest): Option<String> {
        val authorization: String = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION) ?: let { return None }

        AUTHORIZATION_PATTERN.toPattern(Pattern.CASE_INSENSITIVE).matcher(authorization).also {
            if (!it.matches()) {
                return None
            }
            return it.group(MATCHER_TOKEN_NAME).toOption()
        }
    }
}
