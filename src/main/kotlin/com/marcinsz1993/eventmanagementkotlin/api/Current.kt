package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Current(
    @JsonProperty("last_updated_epoch")
    val lastUpdatedEpoch:Int,
    @JsonProperty("last_updated")
    val lastUpdated:String,
    @JsonProperty("temp_c")
    val tempC:Int,
    @JsonProperty("temp_f")
    val tempF:Int,
    @JsonProperty("is_day")
    val isDay:Int,
    val condition: Condition,
    @JsonProperty("wind_mph")
    val windMph:Int,
    @JsonProperty("wind_kph")
    val windKph:Int,
    @JsonProperty("wind_degree")
    val windDegree:Int,
    @JsonProperty("wind_dir")
    val windDir:String,
    @JsonProperty("pressure_mb")
    val pressureMb:Int,
    @JsonProperty("pressure_in")
    val pressureIn:Int,
    @JsonProperty("precip_mm")
    val precipMm:Int,
    val humidity:Int,
    val cloud:Int,
    @JsonProperty("feelslike_c")
    val feelslikeC:Int,
    @JsonProperty("feelslike_f")
    val feelslikeF:Int,
    @JsonProperty("vis_km")
    val visKm:Int,
    @JsonProperty("vis_miles")
    val visMiles:Int,
    val uv:Int,
    @JsonProperty("gust_mph")
    val gustMph:Int,
    @JsonProperty("gust_kph")
    val gustKph:Int
)