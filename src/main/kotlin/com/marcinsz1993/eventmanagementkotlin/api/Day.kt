package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Day(
    @JsonProperty("maxtemp_c")
    val maxtempC:Int,
    @JsonProperty("maxtemp_f")
    val maxtempF:Int,
    @JsonProperty("mintemp_c")
    val mintempC:Int,
    @JsonProperty("mintemp_f")
    val mintempF:Int,
    @JsonProperty("avgtemp_c")
    val avgtempC:Int,
    @JsonProperty("avgtemp_f")
    val avgtempF:Int,
    @JsonProperty("maxwind_mph")
    val maxwindMph:Int,
    @JsonProperty("maxwind_kph")
    val maxwindKph:Int,
    @JsonProperty("totalprecip_mm")
    val totalprecipMm:Int,
    @JsonProperty("totalprecip_in")
    val totalprecipIn:Int,
    @JsonProperty("totalsnow_cm")
    val totalsnowCm:Int,
    @JsonProperty("avgvis_km")
    val avgvisKm:Int,
    @JsonProperty("avgvis_miles")
    val avgvisMiles:Int,
    val avghumidity:Int,
    @JsonProperty("daily_will_it_rain")
    val  dailyWillItRain:Int,
    @JsonProperty("daily_chance_of_rain")
    val  dailyChanceOfRain:Int,
    @JsonProperty("daily_will_it_snow")
    val dailyWillItSnow:Int,
    @JsonProperty("daily_chance_of_snow")
    val dailyChanceOfSnow:Int,
    val condition:Condition,
    val uv:Int
)