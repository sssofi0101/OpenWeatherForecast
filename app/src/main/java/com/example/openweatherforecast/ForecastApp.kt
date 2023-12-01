package com.example.openweatherforecast

import android.app.Application
import androidx.room.Room
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.data.room.AppDatabase
import com.example.openweatherforecast.di.AppComponent
import com.example.openweatherforecast.di.DaggerAppComponent

class ForecastApp : Application() {
    companion object{
        lateinit var database: AppDatabase
        //lateinit var remoteDataSource: WeatherApi
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "mydatabase")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
       // remoteDataSource = RetrofitImpl.getService()

        appComponent = DaggerAppComponent.create()

    }
}