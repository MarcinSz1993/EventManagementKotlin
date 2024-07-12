package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Astro(
    val sunrise:String,
    val sunset:String,
    val moonrise:String,
    val moonset:String,
    @JsonProperty("moon_phase")
    val moonPhase:String,
    @JsonProperty("moon_illumination")
    val moonIllumination:Int,
    @JsonProperty("is_moon_up")
    val isMoonUp:Int,
    @JsonProperty("is_sun_up")
    val isSunUp:Int
)