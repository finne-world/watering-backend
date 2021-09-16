package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter

interface UserService {
    fun getById(userId: Long): UserEntity

    fun getUsers(filter: UserFilter): List<UserEntity>
}
