package com.example.openweatherforecast.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl
{
    private fun createRetrofit(interceptor: HttpLoggingInterceptor) : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getService() : WeatherApi
    {
        return createRetrofit(LoggingInterceptor.interceptor).create(WeatherApi::class.java)
    }

    companion object {
        fun getService() = RetrofitImpl().getService()
    }
}