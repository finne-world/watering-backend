package com.watering.watering_backend.lib.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

fun LocalDateTime.toDate(zoneId: ZoneId = ZoneId.systemDefault()): Date {
    return ZonedDateTime.of(this, zoneId).toInstant().toDate()
}

fun Instant.toDate(): Date {
    return Date.from(this)
}
