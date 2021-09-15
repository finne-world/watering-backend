package com.watering.watering_backend.application.controller.helper

import com.watering.watering_backend.application.json.response.`object`.History
import com.watering.watering_backend.domain.entity.WateringHistoryEntity

fun convertTo(entity: WateringHistoryEntity): History<Int> {
    return History(
        value = entity.amount,
        timestamp = entity.timestamp
    )
}
