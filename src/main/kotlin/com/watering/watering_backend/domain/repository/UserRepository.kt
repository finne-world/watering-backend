package com.watering.watering_backend.domain.repository

import arrow.core.Either
import arrow.core.Option
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter
import com.watering.watering_backend.domain.exception.InsertFailedException

interface UserRepository {
    fun create(username: String, encodedPassword: String, authorities: List<AuthorityEntity>): Either<InsertFailedException, UserEntity>

    fun getById(userId: Long): Option<UserEntity>

    fun getByUsername(username: String): Option<UserEntity>

    fun existsByUsername(username: String): Boolean

    fun getUsersByFilter(filter: UserFilter): List<UserEntity>
}
