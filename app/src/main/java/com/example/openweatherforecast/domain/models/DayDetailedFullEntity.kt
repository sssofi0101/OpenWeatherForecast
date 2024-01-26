package com.example.openweatherforecast.domain.models

data class DayDetailedFullEntity (
    val date: String,
    val max_gust: Double,
    val avg_visibility: Double,
    val humidity:Double,
    val clouds: Double,
    val pressure:Double,
    val wind_speed: Double,
    val max_temp: Double,
    val min_temp: Double
)