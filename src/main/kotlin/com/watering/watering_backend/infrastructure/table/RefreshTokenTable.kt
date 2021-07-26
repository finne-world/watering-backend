package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.RefreshTokenEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object RefreshTokenTable: LongIdTable(name = "refresh_tokens") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val token = uuid("token")
    val expiresAt = datetime("expires_at")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): RefreshTokenEntity {
        return RefreshTokenEntity(
            resultRow[id].value,
            resultRow[userId].value,
            resultRow[token],
            resultRow[expiresAt],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
