package com.watering.watering_backend.domain.entity

//TODO: データベーステーブルが存在しないけど、Entityとして良いのだろうか
data class AccessTokenEntity(
    val token: String,
    val expiresIn: Long
)
