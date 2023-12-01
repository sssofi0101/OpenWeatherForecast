package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.models.MainDayForecastEntity
import retrofit2.Call
import javax.inject.Inject

class MainForecastUseCase @Inject constructor(val repository: Repository) {
    fun getMainForecast(lat:Double, lon:Double) : Call<Forecast> {
        return repository.loadForecast(lat,lon)
    }
    fun getCurrentWeather(lat:Double, lon:Double) : Call<CurrentWeather>{
        return repository.loadCurrentWeather(lat,lon)
    }
    fun saveMainForecast(days : List<MainDayForecastEntity>){
        repository.saveForecast(days)
    }
    fun saveCurrentWeather(currentWeatherEntity: CurrentWeatherEntity){
        repository.saveCurrentWeather(currentWeatherEntity)
    }

    fun getCachedMainForecast() : List<MainDayForecastEntity>{
        return repository.loadCachedForecast()
    }
    fun getCachedCurrentWeather() : CurrentWeatherEntity{
        return repository.loadCachedCurrentWeather()
    }
}