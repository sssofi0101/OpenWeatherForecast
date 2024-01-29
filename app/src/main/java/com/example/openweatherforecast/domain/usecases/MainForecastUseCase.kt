package com.example.openweatherforecast.domain.usecases

import androidx.lifecycle.LiveData
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.models.MainDayForecastEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class MainForecastUseCase @Inject constructor(val repository: Repository) {
    suspend fun getMainForecast(lat:Double, lon:Double) : Forecast {
         val job = coroutineScope {
                async (Dispatchers.IO){
                    repository.loadForecast(lat,lon)
                }
            }
        val result = job.await()
        return result

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