package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.AutoWateringSettingEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object AutoWateringSettingTable: LongIdTable(name = "auto_watering_settings") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val enabled = bool("enabled").default(false)
    val interval = integer("interval").nullable().default(null)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): AutoWateringSettingEntity {
        return AutoWateringSettingEntity(
            resultRow[id].value,
            resultRow[deviceId],
            resultRow[enabled],
            resultRow.getOrNull(interval),
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
