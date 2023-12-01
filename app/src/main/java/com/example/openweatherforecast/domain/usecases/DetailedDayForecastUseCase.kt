package com.example.openweatherforecast.domain.usecases

import com.example.openweatherforecast.data.room.entities.DayDetails
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.DayDetailsEntity
import javax.inject.Inject

class DetailedDayForecastUseCase @Inject constructor(val repository: Repository) {

    fun getCachedDetailedForecast(day:String) : DayDetailsEntity{
        return repository.loadCachedDayDetails(day)
    }

    fun saveDetailedForecast(detailedForecast: DayDetails){

         repository.saveDayDetails(detailedForecast)
    }
}