package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.infrastructure.table.UserAuthorityMapTable
import com.watering.watering_backend.infrastructure.table.UserTable
import com.watering.watering_backend.lib.extension.runIfTrue
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: UserRepository {
    //TODO: 結局マッピングテーブルへのインサートとかもここでやっていのかわからない
    override fun create(username: String, encodedPassword: String, authorities: List<AuthorityEntity>): Either<InsertFailedException, UserEntity> {
        val createdUser: UserEntity = UserTable.insert {
            it[this.username] = username
            it[this.password] = encodedPassword
        }
        .resultedValues
        .orEmpty()
        .map(UserTable::toEntity)
        .firstOrNone()
        .getOrElse {
            return InsertFailedException("Failed to create resource 'user'.").left()
        }

        authorities.forEach { authority ->
            UserAuthorityMapTable.insert {
                it[this.userId] = createdUser.id
                it[this.authorityId] = authority.id
            }
            .resultedValues
            .toOption()
            .isEmpty()
            .runIfTrue {
                return InsertFailedException("Failed to create resource 'user_authority_map'.").left()
            }
        }

        return createdUser.right()
    }

    override fun getById(userId: Long): Option<UserEntity> {
        return UserTable.select { UserTable.id eq userId }.map(UserTable::toEntity).firstOrNone()
    }

    override fun getByUsername(username: String): Option<UserEntity> {
        return UserTable.select { UserTable.username eq username }.map(UserTable::toEntity).firstOrNone()
    }

    override fun existsByUsername(username: String): Boolean {
        return UserTable.select { UserTable.username eq username }.count() > 0
    }
}
