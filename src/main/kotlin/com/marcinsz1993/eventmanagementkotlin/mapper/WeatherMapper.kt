package com.marcinsz1993.eventmanagementkotlin.mapper

import com.marcinsz1993.eventmanagementkotlin.api.WeatherFromApi
import com.marcinsz1993.eventmanagementkotlin.dto.WeatherDto

data object WeatherMapper {
    fun convertWeatherFromApiToWeatherDto(weatherFromApi: WeatherFromApi):WeatherDto{
        val day = weatherFromApi.forecast.forecastday[0].day
        val astro = weatherFromApi.forecast.forecastday[0].astro
        val location = weatherFromApi.location
        val forecastday = weatherFromApi.forecast.forecastday[0]
        return WeatherDto(
            forecastday.date,
            location.name,
            location.country,
            astro.sunrise,
            astro.sunset,
            day.maxtempC,
            day.mintempC,
            day.maxwindKph,
            day.dailyChanceOfRain,
            day.dailyChanceOfSnow
        )

    }

}