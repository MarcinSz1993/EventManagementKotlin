package com.marcinsz1993.eventmanagementkotlin

import com.marcinsz1993.eventmanagementkotlin.mapper.PolishCharactersMapper
import com.marcinsz1993.eventmanagementkotlin.service.EventService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import java.time.LocalDate

@SpringBootApplication
@ConfigurationPropertiesScan
class EventManagementKotlinApplication

fun main(args: Array<String>) {
    val ctx: ApplicationContext = runApplication<EventManagementKotlinApplication>(*args)
    val eventService = ctx.getBean(EventService::class.java)
    println(eventService.isUserAdult(LocalDate.of(2000,10,10)))
    println(PolishCharactersMapper.removePolishCharactersFromLocationField("Poznań"))




}






