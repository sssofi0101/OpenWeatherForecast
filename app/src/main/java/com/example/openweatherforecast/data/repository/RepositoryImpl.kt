package com.example.openweatherforecast.data.repository

import com.example.openweatherforecast.BuildConfig
import com.example.openweatherforecast.data.retrofit.WeatherApi

class RepositoryImpl ( private val remoteDataSource: WeatherApi) {

    fun getForecast(lat:Double,lon:Double){
        remoteDataSource.getForecast(lat,lon,BuildConfig.API_KEY)
    }

}