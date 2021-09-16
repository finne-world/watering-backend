package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.response.`object`.History
import com.watering.watering_backend.domain.entity.TemperatureHistoryEntity
import com.watering.watering_backend.domain.entity.WateringHistoryEntity

fun convertTo(entity: WateringHistoryEntity): History<Int> {
    return History(
        value = entity.amount,
        timestamp = entity.timestamp
    )
}

fun convertTo(entity: TemperatureHistoryEntity): History<Float> {
    return History(
        value = entity.value,
        timestamp = entity.timestamp
    )
}
