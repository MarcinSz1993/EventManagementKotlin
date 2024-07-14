package com.marcinsz1993.eventmanagementkotlin.controller

import com.marcinsz1993.eventmanagementkotlin.dto.WeatherDto
import com.marcinsz1993.eventmanagementkotlin.service.WeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("weather/")
class WeatherController(
    val weatherService: WeatherService
) {
    @GetMapping
    fun getWeatherOnEventDay(@RequestParam eventId: Long): WeatherDto? {
        return weatherService.weatherFromApi(eventId)
    }
}