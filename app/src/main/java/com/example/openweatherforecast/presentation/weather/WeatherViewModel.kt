package com.example.openweatherforecast.presentation.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.usecases.MainForecastUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    var forecast = MutableLiveData<Forecast>()
    var currentWeather = MutableLiveData<CurrentWeatherEntity>()
    val loadState = MutableLiveData<MainWeatherState>()
    private val repository:RepositoryImpl = RepositoryImpl(RetrofitImpl.getService())
    private val mainForecastUseCase = MainForecastUseCase(repository)

    fun loadForecast(lat:Double,lon:Double){
        viewModelScope.launch {
            loadState.postValue(MainWeatherState.LOADING)
            val job1 = viewModelScope.launch {
                try {
                    val response = mainForecastUseCase.getCurrentWeather(lat, lon)
                    response.enqueue(object : Callback<CurrentWeather> {
                        override fun onResponse(
                            call: Call<CurrentWeather>,
                            response: Response<CurrentWeather>
                        ) {
                            if (response.body() != null) {
                                val curWeather = response.body()!!
                                val currentWeatherEntity = CurrentWeatherEntity(
                                    curWeather.name,
                                    "https://openweathermap.org/img/wn/${curWeather.weather.first().icon}@2x.png",
                                    "${curWeather.main.temp} °C",
                                    curWeather.weather.first().description
                                )
                                currentWeather.postValue(currentWeatherEntity)
                            }
                        }

                        override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                            loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
                            TODO("Проверить что за ошибка и если нет интернета, вывести кеш. Иначе toast в fragment")
                        }

                    })
                } catch (e: Exception) {
                    loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
                }
            }
            val job2 = viewModelScope.launch {
                try {
                    val response = mainForecastUseCase.getMainForecast(lat, lon)
                    response.enqueue(object : Callback<Forecast> {
                        override fun onResponse(
                            call: Call<Forecast>,
                            response: Response<Forecast>
                        ) {
                            if (response.body() != null) {
                                forecast.postValue(response.body())
                            }

                        }

                        override fun onFailure(call: Call<Forecast>, t: Throwable) {
                            loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
                            TODO("Not yet implemented")
                        }

                    })
                } catch (e: Exception) {
                    loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
                }
            }
            job1.join()
            job2.join()
            loadState.postValue(MainWeatherState.LOADED)
        }
    }
}