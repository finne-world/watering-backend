package com.watering.watering_backend.domain.service.impl

import arrow.core.getOrElse
import arrow.core.getOrHandle
import com.watering.watering_backend.domain.constant.Authority
import com.watering.watering_backend.domain.constant.Error
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter
import com.watering.watering_backend.domain.exception.application.ApplicationException
import com.watering.watering_backend.domain.exception.application.ResourceCreateFailedException
import com.watering.watering_backend.domain.exception.application.ResourceNotFoundException
import com.watering.watering_backend.domain.exception.application.UserRegistrationFailedException
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.domain.service.UserService
import com.watering.watering_backend.domain.service.dto.user.RegisterUserResult
import com.watering.watering_backend.lib.extension.getErrorDescription
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val authorityRepository: AuthorityRepository,
    private val passwordEncoder: PasswordEncoder
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

    override fun registerUser(username: String, password: String, authorities: List<String>): RegisterUserResult = transaction {
        if (userRepository.existsByUsername(username)) {
            throw UserRegistrationFailedException(
                errorDescription = "the user with username [${username}] already exists.",
                errorMessage = "the user with username [${username}] already exists. please choice another username."
            )
        }

        val authorityEntities: List<AuthorityEntity> = authorityRepository.getAuthoritiesByNameList(authorities).getOrHandle {
            throw UserRegistrationFailedException(
                errorDescription = it.getErrorDescription(),
                errorMessage = "Failed to create the user with authority [${authorities.joinToString(", ")}]. Please choice authorities from [${Authority.values().joinToString(", ")}]"
            )
        }

        val createdUser: UserEntity = userRepository.create(
            username,
            passwordEncoder.encode(password),
            authorityEntities
        ).getOrHandle {
            throw ResourceCreateFailedException(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                error = Error.UNKNOWN_ERROR,
                errorDescription = it.getErrorDescription(),
                errorMessage = ApplicationException.UNKNOWN_MESSAGE
            )
        }

        RegisterUserResult(
            createdUser,
            authorityEntities
        )
    }
}
