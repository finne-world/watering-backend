package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.DeviceEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object DeviceTable: LongIdTable("devices") {
    val uuid = uuid("uuid")
    val name = varchar("name", 255)
    val userId = long("user_id")
    val current = bool("current").default(false)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(id)

    fun toEntity(deviceRow: ResultRow): DeviceEntity {
        return DeviceEntity(
            deviceRow[id].value,
            deviceRow[uuid],
            deviceRow[userId],
            deviceRow[name],
            deviceRow[current],
            deviceRow[createdAt],
            deviceRow[updatedAt]
        )
    }
}
