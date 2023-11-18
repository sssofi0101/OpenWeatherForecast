package com.example.openweatherforecast.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openweatherforecast.data.room.CurrentWeather

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrentWeather(currentWeather: CurrentWeather)

    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather() : CurrentWeather

    @Query("DELETE FROM CurrentWeather")
    fun deleteCurrentWeather()
}