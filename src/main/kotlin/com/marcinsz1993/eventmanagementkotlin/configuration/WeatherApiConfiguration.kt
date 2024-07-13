package com.marcinsz1993.eventmanagementkotlin.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding

@ConfigurationPropertiesBinding
@ConfigurationProperties(prefix = "weather.api")
data class WeatherApiConfiguration(
    val apiKey:String,
    val baseUrl:String
)