package com.watering.watering_backend.config

import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class WebConfig(private val hikariDataSource: HikariDataSource) {
    @PostConstruct
    fun initExposedDatabaseConnection(){
        Database.connect(this.hikariDataSource)
    }
}
