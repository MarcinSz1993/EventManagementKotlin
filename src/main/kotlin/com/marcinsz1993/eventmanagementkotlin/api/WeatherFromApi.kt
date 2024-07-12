package com.marcinsz1993.eventmanagementkotlin.api

data class WeatherFromApi(
    val location:Location,
    val current:Current,
    val forecast:Forecast
)