package com.example.openweatherforecast.data.repository

import android.util.Log
import com.example.openweatherforecast.BuildConfig
import com.example.openweatherforecast.data.retrofit.WeatherApi
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.data.room.AppDatabase
import com.example.openweatherforecast.data.room.entities.Day
import com.example.openweatherforecast.data.room.entities.DayDetails
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.models.DayDetailsEntity
import com.example.openweatherforecast.domain.models.MainDayForecastEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl ( private val remoteDataSource: WeatherApi, private val localDatabase: AppDatabase):Repository {
    override fun loadCurrentWeather(lat: Double, lon: Double) : Call<CurrentWeather> {
       return remoteDataSource.getCurrentWeather(lat,lon,BuildConfig.API_KEY)
    }

    override fun loadForecast(lat:Double,lon:Double): Call<Forecast>{
        return remoteDataSource.getForecast(lat,lon,BuildConfig.API_KEY)
    }

    override fun loadDetailedDayForecast(day: String) : DayDetailsEntity{
        TODO("Not yet implemented")
    }

    override fun saveCurrentWeather(currentWeather: CurrentWeatherEntity) {
        localDatabase.currentWeatherDao().deleteCurrentWeather()
        localDatabase.currentWeatherDao().addCurrentWeather(
            com.example.openweatherforecast.data.room.entities.CurrentWeather(currentWeather.city,
                currentWeather.temp.substringBefore(" ").toDouble(),currentWeather.icon, currentWeather.description))
    }

    override fun saveForecast(days: List<MainDayForecastEntity>) {
        localDatabase.daysDao().deleteAllDays()
        val daysDb = days.map { it -> Day(it.date,it.max_temp,it.min_temp,it.humidity,it.windSpeed,it.pressure,it.cloudiness) }
        localDatabase.daysDao().addAllDays(*daysDb.toTypedArray())
    }

    override fun saveDayDetails(dayDetails: DayDetails) {

        //localDatabase.dayDetailsDao().deleteAllDayDetails()
        localDatabase.dayDetailsDao().addDayDetails(dayDetails)
    }

    override fun loadCachedCurrentWeather(): CurrentWeatherEntity {
        val curWeather = localDatabase.currentWeatherDao().getCurrentWeather()
        return CurrentWeatherEntity(curWeather.city,curWeather.icon,"${curWeather.temp} °C",curWeather.description)
    }

    override fun loadCachedForecast(): List<MainDayForecastEntity> {
        val days = localDatabase.daysDao().selectAllDays()
        return days.map { it -> MainDayForecastEntity(it.date, it.min_temp, it.max_temp, it.clouds, it.pressure, it.humidity,it.wind_speed) }
    }

    override fun loadCachedDayDetails(date: String): DayDetailsEntity {
        return localDatabase.dayDetailsDao().getFullDayInfo(date)
    }

}