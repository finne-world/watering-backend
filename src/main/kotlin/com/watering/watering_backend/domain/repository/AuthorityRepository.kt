package com.watering.watering_backend.domain.repository

import arrow.core.Either
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException

interface AuthorityRepository {
    fun getAuthoritiesByUsername(username: String): List<AuthorityEntity>

    fun getAuthoritiesByNameList(names: List<String>): Either<ResourceNotFoundException, List<AuthorityEntity>>
}
