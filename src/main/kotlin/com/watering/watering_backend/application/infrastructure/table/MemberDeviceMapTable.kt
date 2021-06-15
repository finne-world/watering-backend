package com.watering.watering_backend.application.infrastructure.table

import com.watering.watering_backend.application.domain.entity.MemberDeviceMapEntity
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object MemberDeviceMapTable: LongIdTable(name = "member_device_maps") {
    val memberId = long("member_id")
    val deviceId = reference("device_id", DeviceTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): MemberDeviceMapEntity {
        return MemberDeviceMapEntity(
            resultRow[id].value,
            resultRow[memberId],
            resultRow[deviceId],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}