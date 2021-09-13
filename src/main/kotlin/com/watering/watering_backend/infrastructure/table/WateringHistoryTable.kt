package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.WateringHistoryEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object WateringHistoryTable: LongIdTable(name = "watering_histories") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val amount = integer("amount")
    val timestamp = datetime("timestamp")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): WateringHistoryEntity {
        return WateringHistoryEntity(
            resultRow[this.id].value,
            resultRow[this.deviceId].value,
            resultRow[this.amount],
            resultRow[this.timestamp],
            resultRow[this.createdAt],
            resultRow[this.updatedAt]
        )
    }
}
