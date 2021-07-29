package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.AuthorityEntity

interface AuthorityService {
    fun getAuthoritiesByUsername(username: String): List<AuthorityEntity>
}
