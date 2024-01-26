package com.example.openweatherforecast.domain.models

data class MainDayEntity(val date:String, val max_temp:Double, val min_temp:Double, val humidity:Double,
    val wind_speed:Double, val pressure: Double, val clouds:Double)
