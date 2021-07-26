package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.UserDiscordMapEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserDiscordMap: LongIdTable(name = "user_discord_map") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val discordId = long("discord_id")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): UserDiscordMapEntity {
        return UserDiscordMapEntity(
            resultRow[id].value,
            resultRow[userId].value,
            resultRow[discordId],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
