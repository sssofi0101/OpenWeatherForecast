package com.example.openweatherforecast.domain.interfaces

interface Repository {
    fun loadCurrentWeather(lat:Double,lon:Double)
    fun loadForecast(lat:Double,lon:Double)
    fun loadDayForecast(day:String)
}