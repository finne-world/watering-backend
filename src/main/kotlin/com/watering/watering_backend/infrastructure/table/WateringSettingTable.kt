package com.watering.watering_backend.infrastructure.table

import com.watering.watering_backend.domain.entity.AutomationSettingEntity
import com.watering.watering_backend.domain.entity.SettingEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object SettingTable: LongIdTable(name = "settings") {
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val waterAmount = integer("water_amount").nullable().default(null)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(settingRow: ResultRow, automationSettingEntity: AutomationSettingEntity): SettingEntity {
        return SettingEntity(
            settingRow[this.id].value,
            settingRow[this.deviceId].value,
            settingRow.getOrNull(this.waterAmount),
            automationSettingEntity,
            settingRow[this.createdAt],
            settingRow[this.updatedAt]
        )
    }

    fun toEntity(resultRow: ResultRow): SettingEntity {
        return SettingEntity(
            resultRow[this.id].value,
            resultRow[this.deviceId].value,
            resultRow.getOrNull(this.waterAmount),
            AutomationSettingTable.toEntity(resultRow),
            resultRow[this.createdAt],
            resultRow[this.updatedAt]
        )
    }
}
