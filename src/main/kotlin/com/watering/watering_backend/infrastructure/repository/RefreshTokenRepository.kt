package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.repository.RefreshTokenRepository
import com.watering.watering_backend.infrastructure.table.RefreshTokenTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class RefreshTokenRepository: RefreshTokenRepository {
    override fun create(userId: Long, expiresAt: LocalDateTime): Either<InsertFailedException, RefreshTokenEntity> {
        return RefreshTokenTable.insert {
            it[this.userId] = userId
            it[this.token] = UUID.randomUUID()
            it[this.expiresAt] = expiresAt
        }
        .resultedValues
        .orEmpty()
        .map(RefreshTokenTable::toEntity)
        .firstOrNone()
        .toEither { InsertFailedException("Failed to create resource `refresh_token`.") }
    }

    override fun existsByUserId(userId: Long): Boolean {
        return RefreshTokenTable.select { RefreshTokenTable.userId eq userId }.count() > 0
    }

    override fun deleteByUserId(userId: Long): Boolean {
        return RefreshTokenTable.deleteWhere { RefreshTokenTable.userId eq userId } > 0
    }
}
