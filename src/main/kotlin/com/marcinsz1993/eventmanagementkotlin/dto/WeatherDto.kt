package com.marcinsz1993.eventmanagementkotlin.dto

data class WeatherDto(
    val date:String,
    val cityName:String,
    val county:String,
    val sunrise:String,
    val sunset:String,
    val maxTemperature:Int,
    val minTemperature:Int,
    val maxWind:Int,
    val chanceOfRain:Int,
    val chanceOfSnow:Int
)