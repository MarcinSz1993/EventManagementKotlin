package com.marcinsz1993.eventmanagementkotlin.api

import com.fasterxml.jackson.annotation.JsonProperty

data class Hour(
    @JsonProperty("time_epoch")
    val timeEpochval :Int,
    val time:String,
    @JsonProperty("temp_c")
    val tempCval :Int,
    @JsonProperty("temp_f")
    val tempFval :Int,
    @JsonProperty("is_day")
    val isDayval :Int,
    val condition :Condition,
    @JsonProperty("wind_mph")
    val windMphval :Int,
    @JsonProperty("wind_kph")
    val windKphval :Int,
    @JsonProperty("wind_degree")
    val windDegreeval :Int,
    @JsonProperty("wind_dir")
    val windDir:String,
    @JsonProperty("pressure_mb")
    val pressureMbval :Int,
    @JsonProperty("pressure_in")
    val pressureInval :Int,
    @JsonProperty("precip_mm")
    val precipMmval :Int,
    @JsonProperty("precip_in")
    val precipInval :Int,
    @JsonProperty("snow_cm")
    val snowCmval :Int,
    val humidityval :Int,
    val cloudval :Int,
    @JsonProperty("feelslike_c")
    val feelslikeCval :Int,
    @JsonProperty("feelslike_f")
    val feelslikeFval :Int,
    @JsonProperty("windchill_c")
    val windchillCval :Int,
    @JsonProperty("windchill_f")
    val windchillFval :Int,
    @JsonProperty("heatindex_c")
    val heatindexCval :Int,
    @JsonProperty("heatindex_f")
    val heatindexFval :Int,
    @JsonProperty("dewpoint_c")
    val dewpointCval :Int,
    @JsonProperty("dewpoint_f")
    val dewpointFval :Int,
    @JsonProperty("will_it_rain")
    val willItRainval :Int,
    @JsonProperty("chance_of_rain")
    val chanceOfRainval :Int,
    @JsonProperty("will_it_snow")
    val willItSnowval :Int,
    @JsonProperty("chance_of_snow")
    val chanceOfSnowval :Int,
    @JsonProperty("vis_km")
    val visKmInt:Int,
    @JsonProperty("vis_miles")
    val visMilesInt:Int,
    @JsonProperty("gust_mph")
    val gustMphInt:Int,
    @JsonProperty("gust_kph")
    val gustKphInt:Int,
    val uv:Int
)