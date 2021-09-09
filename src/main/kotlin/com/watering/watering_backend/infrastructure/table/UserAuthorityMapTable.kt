package com.watering.watering_backend.infrastructure.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserAuthorityMapTable: LongIdTable(name = "user_authority_maps") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val authorityId = reference("authority_id", AuthorityTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}
