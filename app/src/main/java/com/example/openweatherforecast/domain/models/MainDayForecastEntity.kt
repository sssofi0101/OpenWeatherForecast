package com.example.openweatherforecast.domain.models

data class MainDayForecastEntity(
    var date:String, var min_temp:Double, var max_temp:Double,
    var cloudiness: Double, var pressure:Double, var humidity:Double,
    var windSpeed:Double)
