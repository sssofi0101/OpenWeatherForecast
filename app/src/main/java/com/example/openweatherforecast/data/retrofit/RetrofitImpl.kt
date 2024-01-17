package com.example.openweatherforecast.data.retrofit

import com.example.openweatherforecast.ForecastApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitImpl
{
    @Inject
    lateinit var retrofit : Retrofit

    init {
        ForecastApp.appComponent.inject(this)
    }
    private fun getService() : WeatherApi
    {
        return retrofit.create(WeatherApi::class.java)
    }
    companion object {
        fun getService() = RetrofitImpl().getService()
    }
}