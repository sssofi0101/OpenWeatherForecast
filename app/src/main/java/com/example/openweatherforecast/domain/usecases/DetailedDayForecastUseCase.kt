package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.domain.interfaces.Repository

class DetailedDayForecastUseCase(val repository: Repository) {
    fun getDetailedForecast(day:String)
    {
        repository.loadDayForecast(day)
    }
}