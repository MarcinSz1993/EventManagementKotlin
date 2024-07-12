package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Location(
    val name:String,
    val region:String,
    val country:String,
    val lat:Int,
    val lon:Int,
    @JsonProperty("tz_id")
    val tzId:String,
    @JsonProperty("localtime_epoch")
    val localtimeEpoch:Int,
    val localtime:String
)