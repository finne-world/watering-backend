package com.watering.watering_backend.domain.security.service

import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.security.model.UserDetailsImpl
import com.watering.watering_backend.lib.extension.getOrThrow
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
): UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserEntity = userRepository.getByUsername(username)
                                             .toEither { UsernameNotFoundException("User Not Found with username: $username") }
                                             .getOrThrow()

        return UserDetailsImpl(
            user.id,
            user.username,
            user.password,
            user.authorities
        )
    }
}
