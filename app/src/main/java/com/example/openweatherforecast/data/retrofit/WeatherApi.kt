package com.example.openweatherforecast.data.retrofit

import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?units=metric&lang=ru")
    @Headers("Content-type: application/json")
    fun getForecast(@Query("lat") lat:Double,@Query("lon") lon:Double,@Query("appid") appid:String): Call<Forecast>

    @GET("weather?units=metric&lang=ru")
    @Headers("Content-type: application/json")
    fun getCurrentWeather(@Query("lat") lat:Double,@Query("lon") lon:Double,@Query("appid") appid:String): Call<CurrentWeather>


}