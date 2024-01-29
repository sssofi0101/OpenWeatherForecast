package com.example.openweatherforecast.domain.interfaces

import androidx.lifecycle.LiveData
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.models.DayDetailedFullEntity
import com.example.openweatherforecast.domain.models.DayDetailsEntity
import com.example.openweatherforecast.domain.models.MainDayForecastEntity
import retrofit2.Call

interface Repository {
    suspend fun loadCurrentWeather(lat:Double, lon:Double) : CurrentWeatherEntity
    suspend fun loadForecast(lat:Double, lon:Double) : Forecast

    fun saveCurrentWeather(currentWeather: CurrentWeatherEntity)
    fun saveForecast(days:List<MainDayForecastEntity>)
    fun saveDayDetails(dayDetails: DayDetailsEntity)

    fun loadCachedCurrentWeather():CurrentWeatherEntity
    fun loadCachedForecast() : List<MainDayForecastEntity>
    fun loadCachedDayDetails(date : String) : DayDetailedFullEntity
}