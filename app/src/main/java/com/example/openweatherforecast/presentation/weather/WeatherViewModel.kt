package com.example.openweatherforecast.presentation.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.interfaces.Repository
import com.example.openweatherforecast.domain.usecases.DetailedDayForecastUseCase
import com.example.openweatherforecast.domain.usecases.MainForecastUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class WeatherViewModel:ViewModel() {
    var forecast = MutableLiveData<Forecast>()
    var currentWeather = MutableLiveData<CurrentWeather>()
    private val repository:RepositoryImpl = RepositoryImpl(RetrofitImpl.getService())
    private val mainForecastUseCase = MainForecastUseCase(repository)

    fun loadForecast(lat:Double,lon:Double){

        viewModelScope.launch {
            try {
                val response = mainForecastUseCase.getCurrentWeather(lat,lon)
                response.enqueue(object : Callback<CurrentWeather>{
                    override fun onResponse(
                        call: Call<CurrentWeather>,
                        response: Response<CurrentWeather>
                    ) {
                        if (response.body() != null){
                            currentWeather.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
            catch (e:Exception) {

            }
        }
        viewModelScope.launch {
            try {
                val response = mainForecastUseCase.getMainForecast(lat,lon)
                response.enqueue(object : Callback<Forecast>{
                    override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                        if (response.body() != null){
                            forecast.postValue(response.body())
                        }

                    }

                    override fun onFailure(call: Call<Forecast>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
            catch (e:Exception) {

            }
        }
    }
}