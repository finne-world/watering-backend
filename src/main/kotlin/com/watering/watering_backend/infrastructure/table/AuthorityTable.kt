package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.AuthorityEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object AuthorityTable: LongIdTable(name = "authorities") {
    val name = varchar("name", 255)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): AuthorityEntity {
        return AuthorityEntity(
            resultRow[id].value,
            resultRow[name],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
