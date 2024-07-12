package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Forecastday(
    val date:String,
    @JsonProperty("date_epoch")
    val dateEpoch:Int,
    val day:Day,
    val astro:Astro,
    val hour:List<Hour>

)