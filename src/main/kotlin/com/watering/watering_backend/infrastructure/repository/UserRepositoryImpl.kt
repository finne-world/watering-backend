package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.firstOrNone
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import com.watering.watering_backend.domain.entity.filter.UserFilter
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.UserRepository
import com.watering.watering_backend.infrastructure.table.AuthorityTable
import com.watering.watering_backend.infrastructure.table.UserAuthorityMapTable
import com.watering.watering_backend.infrastructure.table.UserDiscordMapTable
import com.watering.watering_backend.infrastructure.table.UserTable
import com.watering.watering_backend.lib.extension.insert
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl: UserRepository {
    //TODO: 結局マッピングテーブルへのインサートとかもここでやっていのかわからない
    override fun create(username: String, encodedPassword: String, authorities: List<AuthorityEntity>): Either<InsertFailedException, UserEntity> {
        val createdUserRow: ResultRow = UserTable.insert {
            it[this.username] = username
            it[this.password] = encodedPassword
        }
        .getOrElse {
            return InsertFailedException("Failed to create resource [${UserTable.tableName}].").left()
        }

        authorities.forEach { authority ->
            UserAuthorityMapTable.insert {
                it[this.userId] = createdUserRow[UserTable.id]
                it[this.authorityId] = authority.id
            }
            .getOrElse {
                return InsertFailedException("Failed to create resource [${UserAuthorityMapTable.tableName}].").left()
            }
        }

        return UserTable.toEntity(createdUserRow, authorities).right()
    }

    override fun getById(userId: Long): Option<UserEntity> {
        return getByCondition { UserTable.id eq userId }
    }

    override fun getByUsername(username: String): Option<UserEntity> {
        return getByCondition { UserTable.username eq username }
    }

    // TODO: inner join してクエリ発行回数を1回にしたほうがいいのかな。でもそうするとコードがちょっとカッコ悪くなってしまう
    private fun getByCondition(where: SqlExpressionBuilder.() -> Op<Boolean>): Option<UserEntity> {
        val userRow: ResultRow = UserTable.select(where).firstOrNone().getOrElse { return None }

        val authorities: List<AuthorityEntity> = (AuthorityTable innerJoin UserAuthorityMapTable).select {
            UserAuthorityMapTable.userId eq userRow[UserTable.id].value
        }
        .map(AuthorityTable::toEntity)

        return UserTable.toEntity(userRow, authorities).toOption()
    }

    override fun existsByUsername(username: String): Boolean {
        return UserTable.select { UserTable.username eq username }.count() > 0
    }

    override fun getUsersByFilter(filter: UserFilter): List<UserEntity> {
        UserTable.selectAll().also { query ->
            filter.name?.also {
                query.andWhere { UserTable.username like it }
            }
            filter.discordId?.also {
                query.andWhere { UserDiscordMapTable.discordId eq it }
            }
        }
        .toList()
        .let { resultRows ->
            val authorityRows: List<ResultRow> = (AuthorityTable innerJoin UserAuthorityMapTable).select {
                UserAuthorityMapTable.userId inList resultRows.map { it[UserTable.id] }
            }
            .toList()

            resultRows.map { resultRow ->
                UserTable.toEntity(resultRow, authorityRows.filter { it[UserAuthorityMapTable.userId] == resultRow[UserTable.id] }.map(AuthorityTable::toEntity))
            }
        }
        .also {
            return it
        }
    }
}
