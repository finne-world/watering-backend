package com.watering.watering_backend.domain.security.filter

import arrow.core.getOrElse
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.service.AuthorityService
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
    private val jwtVerifier: JWTVerifier,
    private val authorityService: AuthorityService,
    private val userDetailsService: UserDetailsService,
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

        val username: String = this.jwtVerifier.verify(token).let { it.subject }

        val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(username)
        val authorities: List<AuthorityEntity> = this.authorityService.getAuthoritiesByUsername(userDetails.username)

        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, authorities.map { SimpleGrantedAuthority("ROLE_${it.name}") }).also {
            it.details = WebAuthenticationDetailsSource().buildDetails(request)
        }
        SecurityContextHolder.getContext().let { it.authentication = authentication }

        filterChain.doFilter(request, response)
    }
}
