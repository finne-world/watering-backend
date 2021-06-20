package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.DeviceEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object DeviceTable: Table("devices") {
    val id = uuid("id")
    val name = varchar("name", 255)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(id)

    fun toEntity(resultRow: ResultRow): DeviceEntity {
        return DeviceEntity(
            resultRow[id],
            resultRow[name],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
