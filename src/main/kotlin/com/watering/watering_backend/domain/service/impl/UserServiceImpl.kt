package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    @Transactional
    override fun getById(userId: Long): UserEntity {
        this.userRepository.getById(userId).getOrElse {
            throw ResourceNotFoundException(
                errorDescription = "Resource user with user_id [${userId}] does not exists in database.",
                errorMessage = "the user with user id [${userId}] not found."
            )
        }.also {
            return it
        }
    }

    @Transactional
    override fun getUsers(filter: UserFilter): List<UserEntity> {
        return this.userRepository.getUsersByFilter(filter)
    }
}
