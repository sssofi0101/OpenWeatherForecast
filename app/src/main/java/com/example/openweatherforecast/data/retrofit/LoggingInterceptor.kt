package com.example.openweatherforecast.data.retrofit

import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {
    companion object {
        val interceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}