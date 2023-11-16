package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.domain.interfaces.Repository

class MainForecastUseCase (val repository: Repository) {
    fun getMainForecast(lat:Double, lon:Double) {
        repository.loadForecast(lat,lon)
        repository.loadCurrentWeather(lat,lon)
    }
}