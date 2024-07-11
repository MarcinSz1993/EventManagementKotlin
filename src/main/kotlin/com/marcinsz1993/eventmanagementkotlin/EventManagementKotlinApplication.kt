package com.marcinsz1993.eventmanagementkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EventManagementKotlinApplication

fun main(args: Array<String>) {
    runApplication<EventManagementKotlinApplication>(*args)
}
