package com.example.openweatherforecast.data.repository

import android.util.Log
import com.example.openweatherforecast.BuildConfig
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl ( private val remoteDataSource: WeatherApi):Repository {
    override fun loadCurrentWeather(lat: Double, lon: Double) {
        TODO("Not yet implemented")
    }

    override fun loadForecast(lat:Double,lon:Double){
        val response = remoteDataSource.getForecast(lat,lon,BuildConfig.API_KEY)
        response.enqueue(object : Callback<Forecast>{
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                if (response.body() != null) {
                    Log.d("api","ok")
                }
                else {
                    Log.d("api","not ok")
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.d("api","not ok")
            }

        })
    }



    override fun loadDayForecast(day: String) {
        TODO("Not yet implemented")
    }

}