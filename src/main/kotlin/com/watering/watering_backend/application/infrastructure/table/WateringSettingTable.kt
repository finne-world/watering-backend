package com.watering.watering_backend.application.infrastructure.table

import com.watering.watering_backend.application.domain.entity.WateringSettingEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object WateringSettingTable: LongIdTable(name = "watering_settings") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val autoWatering = bool("auto_watering").default(false)
    val interval = integer("interval").nullable().default(null)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): WateringSettingEntity {
        return WateringSettingEntity(
            resultRow[id].value,
            resultRow[deviceId],
            resultRow[autoWatering],
            resultRow.getOrNull(interval),
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
