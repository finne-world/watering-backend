package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.AuthorityEntity
import com.watering.watering_backend.domain.entity.UserEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserTable: LongIdTable(name = "users") {
    val username = varchar("username", 255)
    val password = varchar("password", 255)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(userRow: ResultRow, authorities: List<AuthorityEntity>): UserEntity {
        return UserEntity(
            userRow[id].value,
            userRow[username],
            userRow[password],
            authorities,
            userRow[createdAt],
            userRow[updatedAt]
        )
    }
}
