package com.watering.watering_backend.domain.entity.form

import com.watering.watering_backend.domain.entity.DeviceEntity
import java.util.UUID

data class DeviceForm(
    val id: Long?,
    val serial: UUID?,
    val userId: Long?,
    val name: String?,
    val current: Boolean?,
): Form<DeviceEntity>() {
    override fun getUnmodifiableProperties(entity: DeviceEntity): Map<PropertyName, FormEntityValueMapping> {
        return mapOf(
            PropertyName("id") to FormEntityValueMapping(this.id, entity.id),
            PropertyName("serial") to FormEntityValueMapping(this.serial, entity.serial),
            PropertyName("user_id") to FormEntityValueMapping(this.userId, entity.userId)
        )
    }
}
