package com.watering.watering_backend.domain.security.filter

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.auth0.jwt.exceptions.TokenExpiredException
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.entity.AccessTokenEntity
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.exception.authentication.AuthenticationFailedException
import com.watering.watering_backend.domain.service.AuthorityService
import com.watering.watering_backend.domain.service.shared.AccessTokenSharedService
import com.watering.watering_backend.lib.BearerTokenResolver
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//TODO: DIしていいものなのだろうか
@Component
class JwtAuthenticationTokenFilter(
    private val authorityService: AuthorityService,
    private val userDetailsService: UserDetailsService,
    private val accessTokenSharedService: AccessTokenSharedService,
    private val bearerTokenResolver: BearerTokenResolver
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String = this.bearerTokenResolver.resolve(request).getOrElse {
            filterChain.doFilter(request, response)
            return
        }

        val accessTokenEntity: AccessTokenEntity = this.accessTokenSharedService.verifyAccessToken(token).getOrHandle {
            when (it) {
                is TokenExpiredException -> throw AuthenticationFailedException(
                    error = Error.EXPIRED_ACCESS_TOKEN,
                    errorDescription = "the access token [${token}] was expired.",
                )
                else -> throw AuthenticationFailedException(
                    error = Error.INVALID_ACCESS_TOKEN,
                    errorDescription = "the access token [${token}] was invalid.",
                )
            }
        }

        val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(accessTokenEntity.subject)
        val authorities: List<AuthorityEntity> = this.authorityService.getAuthoritiesByUsername(userDetails.username)

        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, authorities.map { SimpleGrantedAuthority("ROLE_${it.name}") }).also {
            it.details = WebAuthenticationDetailsSource().buildDetails(request)
        }
        SecurityContextHolder.getContext().let { it.authentication = authentication }

        filterChain.doFilter(request, response)
    }
}
