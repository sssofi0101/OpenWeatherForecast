package com.example.openweatherforecast.data.repository

import android.util.Log
import android.widget.Toast
import com.example.openweatherforecast.BuildConfig
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.domain.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl ( private val remoteDataSource: WeatherApi) {

    fun getForecast(lat:Double,lon:Double){
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

}