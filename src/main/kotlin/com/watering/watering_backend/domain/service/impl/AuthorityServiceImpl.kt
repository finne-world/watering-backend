package com.watering.watering_backend.domain.service.impl

import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.service.AuthorityService
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class AuthorityServiceImpl(
    private val authorityRepository: AuthorityRepository,
): AuthorityService {
    override fun getAuthoritiesByUsername(username: String): List<AuthorityEntity> = transaction {
        authorityRepository.getAuthoritiesByUsername(username)
    }
}
