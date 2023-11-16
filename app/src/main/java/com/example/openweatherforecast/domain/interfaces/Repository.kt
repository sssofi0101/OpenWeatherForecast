package com.example.openweatherforecast.domain.interfaces

import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import retrofit2.Call

interface Repository {
    fun loadCurrentWeather(lat:Double,lon:Double) : Call<CurrentWeather>
    fun loadForecast(lat:Double,lon:Double) : Call<Forecast>
    fun loadDayForecast(day:String)
}