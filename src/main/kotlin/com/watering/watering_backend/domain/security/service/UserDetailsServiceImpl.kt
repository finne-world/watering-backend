package com.watering.watering_backend.domain.security.service

import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.security.model.UserDetailsImpl
import com.watering.watering_backend.lib.extension.getOrThrow
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = transaction {
        val user: UserEntity = userRepository.getByUsername(username)
                                             .toEither { UsernameNotFoundException("User Not Found with username: $username") }
                                             .getOrThrow()

        val authorities: List<AuthorityEntity> = authorityRepository.getAuthoritiesByUsername(user.username)

        UserDetailsImpl(
            user.id,
            user.username,
            user.password,
            authorities.map { SimpleGrantedAuthority(it.name) }
        )
    }
}
