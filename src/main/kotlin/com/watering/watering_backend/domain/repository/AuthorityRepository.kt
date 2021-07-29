package com.watering.watering_backend.domain.repository

import arrow.core.Either
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.exception.AuthorityNotFoundException

interface AuthorityRepository {
    fun getAuthoritiesByUsername(username: String): List<AuthorityEntity>

    fun getAuthoritiesByNameList(names: List<String>): Either<AuthorityNotFoundException, List<AuthorityEntity>>
}
