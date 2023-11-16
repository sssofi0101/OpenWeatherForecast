package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import retrofit2.Call

class MainForecastUseCase (val repository: Repository) {
    fun getMainForecast(lat:Double, lon:Double) : Call<Forecast> {
        return repository.loadForecast(lat,lon)
    }
    fun getCurrentWeather(lat:Double, lon:Double) : Call<CurrentWeather>{
        return repository.loadCurrentWeather(lat,lon)
    }
}