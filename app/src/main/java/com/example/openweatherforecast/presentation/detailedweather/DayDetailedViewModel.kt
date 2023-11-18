package com.example.openweatherforecast.presentation.detailedweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweatherforecast.ForecastApp
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.domain.models.DayDetailsEntity
import com.example.openweatherforecast.domain.usecases.DetailedDayForecastUseCase
import com.example.openweatherforecast.domain.usecases.MainForecastUseCase

class DayDetailedViewModel: ViewModel() {
    val dayDetails = MutableLiveData<DayDetailsEntity>()
    private val repository: RepositoryImpl = RepositoryImpl(
        ForecastApp.remoteDataSource,
        ForecastApp.database)
    private val mainForecastUseCase = MainForecastUseCase(repository)
    private val detailedDayForecastUseCase = DetailedDayForecastUseCase(repository)

    fun loadDetails(date: String) {
        val result = detailedDayForecastUseCase.getCachedDetailedForecast(date)
        dayDetails.postValue(result)
    }
}