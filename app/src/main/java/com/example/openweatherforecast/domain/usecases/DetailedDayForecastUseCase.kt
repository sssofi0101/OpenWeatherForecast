package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.data.room.entities.DayDetails
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.DayDetailsEntity

class DetailedDayForecastUseCase(val repository: Repository) {
    fun getDetailedForecast(day:String) : DayDetailsEntity
    {
        return repository.loadDetailedDayForecast(day)
    }

    fun getCachedDetailedForecast(day:String) : DayDetailsEntity{
        return repository.loadCachedDayDetails(day)
    }

    fun saveDetailedForecast(detailedForecast: DayDetails){

         repository.saveDayDetails(detailedForecast)
    }
}