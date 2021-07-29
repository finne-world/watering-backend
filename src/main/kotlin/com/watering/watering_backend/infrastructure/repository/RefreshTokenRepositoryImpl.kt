package com.watering.watering_backend.infrastructure.repository

import arrow.core.Either
import arrow.core.Option
import arrow.core.firstOrNone
import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import com.watering.watering_backend.domain.exception.InsertFailedException
import com.watering.watering_backend.domain.exception.UpdateFailedException
import com.watering.watering_backend.domain.repository.RefreshTokenRepository
import com.watering.watering_backend.infrastructure.table.RefreshTokenTable
import com.watering.watering_backend.lib.extension.isPositive
import com.watering.watering_backend.lib.extension.runIf
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class RefreshTokenRepositoryImpl: RefreshTokenRepository {
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
        .toEither { InsertFailedException("Failed to insert resource in table [refresh_tokens].") }
    }

    override fun updateToken(tokenId: Long, tokenUUID: UUID) {
        return RefreshTokenTable.update({ RefreshTokenTable.id eq tokenId }) {
            it[this.token] = tokenUUID
        }.isPositive().runIf(false) {
            throw UpdateFailedException("Failed to update resource in table [refresh_tokens].")
        }
    }

    override fun getById(id: Long): Option<RefreshTokenEntity> {
        return RefreshTokenTable.select { RefreshTokenTable.id eq id }.map(RefreshTokenTable::toEntity).firstOrNone()
    }

    override fun getByUserId(userId: Long): Option<RefreshTokenEntity> {
        return RefreshTokenTable.select { RefreshTokenTable.userId eq userId }.map(RefreshTokenTable::toEntity).firstOrNone()
    }

    override fun getByUUID(tokenUUID: UUID): Option<RefreshTokenEntity> {
        return RefreshTokenTable.select { RefreshTokenTable.token eq tokenUUID }.map(RefreshTokenTable::toEntity).firstOrNone()
    }

    override fun existsByUserId(userId: Long): Boolean {
        return RefreshTokenTable.select { RefreshTokenTable.userId eq userId }.count() > 0
    }
}
