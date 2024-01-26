package com.example.openweatherforecast.presentation.detailedweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweatherforecast.ForecastApp
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.domain.models.DayDetailedFullEntity
import com.example.openweatherforecast.domain.usecases.DetailedDayForecastUseCase
import javax.inject.Inject

class DayDetailedViewModel: ViewModel() {

    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var detailedDayForecastUseCase: DetailedDayForecastUseCase

    init {
        ForecastApp.appComponent.inject(this)
    }

    val dayDetails = MutableLiveData<DayDetailedFullEntity>()

    fun loadDetails(date: String) {
        val result = detailedDayForecastUseCase.getCachedDetailedForecast(date)
        dayDetails.postValue(result)
    }


}