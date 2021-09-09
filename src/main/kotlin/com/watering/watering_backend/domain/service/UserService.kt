package com.watering.watering_backend.domain.service

import com.watering.watering_backend.domain.entity.UserEntity

interface UserService {
    fun getById(userId: Long): UserEntity
}
