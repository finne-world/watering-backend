package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.UserAuthorityMapEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserAuthorityMapTable: LongIdTable(name = "user_authority_map") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val authorityId = reference("authority_id", AuthorityTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): UserAuthorityMapEntity {
        return UserAuthorityMapEntity(
            resultRow[id].value,
            resultRow[userId].value,
            resultRow[authorityId].value,
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
