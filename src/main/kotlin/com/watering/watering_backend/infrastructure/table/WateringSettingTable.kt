package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.WateringSettingEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object WateringSettingTable: LongIdTable(name = "watering_settings") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val waterAmount = integer("water_amount").nullable().default(null)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): WateringSettingEntity {
        return WateringSettingEntity(
            resultRow[id].value,
            resultRow[deviceId],
            resultRow.getOrNull(waterAmount),
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}
