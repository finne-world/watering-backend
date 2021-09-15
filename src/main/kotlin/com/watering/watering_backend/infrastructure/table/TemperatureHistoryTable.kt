package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object TemperatureHistoryTable: LongIdTable(name = "temperature_histories") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val value = float("value")
    val timestamp = datetime("timestamp")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): TemperatureHistoryEntity {
        return TemperatureHistoryEntity(
            resultRow[this.id].value,
            resultRow[this.deviceId].value,
            resultRow[this.value],
            resultRow[this.timestamp],
            resultRow[this.createdAt],
            resultRow[this.updatedAt]
        )
    }
}
