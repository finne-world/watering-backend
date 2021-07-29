package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.watering.watering_backend.domain.constant.Authority
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.exception.ResourceNotFoundException
import com.watering.watering_backend.domain.repository.AuthorityRepository
import com.watering.watering_backend.infrastructure.table.AuthorityTable
import com.watering.watering_backend.infrastructure.table.UserAuthorityMapTable
import com.watering.watering_backend.infrastructure.table.UserTable
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class AuthorityRepositoryImpl: AuthorityRepository {
    override fun getAuthoritiesByUsername(username: String): List<AuthorityEntity> {
        return (AuthorityTable innerJoin UserAuthorityMapTable innerJoin UserTable).select {
            UserTable.username eq username
        }
        .map(AuthorityTable::toEntity)
    }

    override fun getAuthoritiesByNameList(names: List<String>): Either<ResourceNotFoundException, List<AuthorityEntity>> {
        //TODO: サービスクラスに書くべきなのか迷う
        val invalidNames: List<String> = names.filter {
            !Authority.values().map {
                authority -> authority.name
            }.contains(it.uppercase())
        }
        if (invalidNames.isNotEmpty()) {
            return ResourceNotFoundException(
                errorDescription = "Invalid authority name. [${invalidNames.joinToString(", ")}]"
            ).left()
        }

        return AuthorityTable.select { AuthorityTable.name inList names }.map(AuthorityTable::toEntity).right()
    }
}
