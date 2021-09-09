package com.watering.watering_backend.config

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class TransactionConfig(
    private val dataSource: DataSource
): TransactionManagementConfigurer {
    @Bean
    override fun annotationDrivenTransactionManager(): PlatformTransactionManager {
        return SpringTransactionManager(this.dataSource)
    }
}
