package com.watering.watering_backend.domain.security.model

import com.watering.watering_backend.domain.entity.AuthorityEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    private val id: Long,
    private val username: String,
    private val password: String,
    private val authorities: List<AuthorityEntity>
): UserDetails {
    override fun getUsername(): String {
        return this.username
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        return this.authorities.map { SimpleGrantedAuthority("ROLE_${it.name}") }
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
