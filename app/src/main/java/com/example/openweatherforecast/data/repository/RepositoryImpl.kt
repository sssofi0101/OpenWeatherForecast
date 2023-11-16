package com.example.openweatherforecast.data.repository

import android.util.Log
import com.example.openweatherforecast.BuildConfig
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl ( private val remoteDataSource: WeatherApi):Repository {
    override fun loadCurrentWeather(lat: Double, lon: Double) : Call<CurrentWeather> {
       return remoteDataSource.getCurrentWeather(lat,lon,BuildConfig.API_KEY)
    }

    override fun loadForecast(lat:Double,lon:Double): Call<Forecast>{
        return remoteDataSource.getForecast(lat,lon,BuildConfig.API_KEY)
    }



    override fun loadDayForecast(day: String) {
        TODO("Not yet implemented")
    }

}