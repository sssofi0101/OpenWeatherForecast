package com.example.openweatherforecast.presentation.detailedweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openweatherforecast.ForecastApp
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.di.AppComponent
import com.example.openweatherforecast.di.DaggerAppComponent
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.models.DayDetailsEntity
import com.example.openweatherforecast.domain.usecases.DetailedDayForecastUseCase
import dagger.internal.DaggerCollections
import dagger.internal.DaggerGenerated
import javax.inject.Inject

class DayDetailedViewModel: ViewModel() {

    @Inject
    lateinit var repository: RepositoryImpl

    @Inject
    lateinit var detailedDayForecastUseCase: DetailedDayForecastUseCase

    init {
        ForecastApp.appComponent.inject(this)
    }

    val dayDetails = MutableLiveData<DayDetailsEntity>()
//    private val repository: RepositoryImpl = RepositoryImpl(
//        ForecastApp.remoteDataSource,
//        ForecastApp.database)
//    //private val repository:RepositoryImpl = ForecastApp.appComponent.repositoryImpl
//    private val detailedDayForecastUseCase = DetailedDayForecastUseCase(repository)

    fun loadDetails(date: String) {
        val result = detailedDayForecastUseCase.getCachedDetailedForecast(date)
        dayDetails.postValue(result)
    }


}