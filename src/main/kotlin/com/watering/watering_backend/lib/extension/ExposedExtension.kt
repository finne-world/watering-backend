package com.watering.watering_backend.lib.extension

import arrow.core.Option
import arrow.core.firstOrNone
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement

fun <T: Table> T.insert(body: T.(InsertStatement<Number>) -> Unit): Option<ResultRow> {
    this.insert(body)
        .resultedValues
        .orEmpty()
        .firstOrNone()
        .also { return it }
}
