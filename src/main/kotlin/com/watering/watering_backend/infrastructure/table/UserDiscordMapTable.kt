package com.watering.watering_backend.infrastructure.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UserDiscordMapTable: LongIdTable(name = "user_discord_maps") {
    val userId = reference("user_id", UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val discordId = long("discord_id")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}
