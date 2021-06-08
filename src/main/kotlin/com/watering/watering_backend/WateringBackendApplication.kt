package com.watering.watering_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WateringBackendApplication

fun main(args: Array<String>) {
	runApplication<WateringBackendApplication>(*args)
}
