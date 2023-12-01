package com.example.openweatherforecast.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.openweatherforecast.data.room.daos.CurrentWeatherDao
import com.example.openweatherforecast.data.room.daos.DayDetailsDao
import com.example.openweatherforecast.data.room.daos.DaysDao
import com.example.openweatherforecast.data.room.entities.CurrentWeather
import com.example.openweatherforecast.data.room.entities.Day
import com.example.openweatherforecast.data.room.entities.DayDetails

@Database(entities = [Day::class, DayDetails::class, CurrentWeather::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun daysDao(): DaysDao
    abstract fun dayDetailsDao(): DayDetailsDao
    abstract fun currentWeatherDao(): CurrentWeatherDao

}