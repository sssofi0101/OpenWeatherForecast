package com.example.openweatherforecast.presentation.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweatherforecast.data.repository.RepositoryImpl
import com.example.openweatherforecast.data.retrofit.RetrofitImpl
import com.example.openweatherforecast.data.retrofit.models.CurrentWeather
import com.example.openweatherforecast.data.retrofit.models.Forecast
import com.example.openweatherforecast.domain.models.CurrentWeatherEntity
import com.example.openweatherforecast.domain.models.MainDayForecastEntity
import com.example.openweatherforecast.domain.usecases.MainForecastUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.DateFormat

class WeatherViewModel:ViewModel() {
    var forecast = MutableLiveData<List<MainDayForecastEntity>>()
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
                            else {
                                loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
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
                                val entities = getMainDayForecastEntities(response.body()!!)
                                 forecast.postValue(entities)
                            }
                            else {
                                loadState.postValue(MainWeatherState.error("Произошла ошибка при получении данных"))
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


    private fun getMainDayForecastEntities(body: Forecast) : MutableList<MainDayForecastEntity> {
        val curUnixDate = ("${body.list.first().dt}"+"000").toLong()
        val lastDate = getDate(curUnixDate,5)

        val days = body.list.takeWhile { ("${it.dt}"+"000").toLong() <= lastDate.time }
        val daysForecastEntities:MutableList<MainDayForecastEntity> = mutableListOf(
            MainDayForecastEntity("",1000.0,-1000.0,0.0,0.0,0.0,0.0),
            MainDayForecastEntity("",1000.0,-1000.0,0.0,0.0,0.0,0.0),
            MainDayForecastEntity("",1000.0,-1000.0,0.0,0.0,0.0,0.0),
            MainDayForecastEntity("",1000.0,-1000.0,0.0,0.0,0.0,0.0),
            MainDayForecastEntity("",1000.0,-1000.0,0.0,0.0,0.0,0.0))

        fun editInfo(i: Int,d:Int) {
            daysForecastEntities[i].apply {
                date = getStringDay(getDate(curUnixDate, i+1))
                cloudiness += days[d].clouds.all
                pressure += days[d].main.pressure
                humidity += days[d].main.humidity
                windSpeed += days[d].wind.speed
                if (days[d].main.temp_min < min_temp) {
                    min_temp = days[d].main.temp_min
                }
                if (days[d].main.temp_max > max_temp) {
                    max_temp = days[d].main.temp_max
                }
            }
        }
        var timestampcount1 = 0
        var timestampcount2 = 0
        var timestampcount3 = 0
        var timestampcount4 = 0
        var timestampcount5 = 0

        for (d in 0 until days.size){
            when (getStringDay(Date(("${days[d].dt}"+"000").toLong()))){
                getStringDay(getDate(curUnixDate,1)) -> {
                    editInfo(0,d)
                    timestampcount1 += 1
                }
                getStringDay(getDate(curUnixDate,2)) -> {
                    editInfo(1,d)
                    timestampcount2 += 1
                }
                getStringDay(getDate(curUnixDate,3)) -> {
                    editInfo(2,d)
                    timestampcount3 += 1
                }
                getStringDay(getDate(curUnixDate,4)) -> {
                    editInfo(3,d)
                    timestampcount4 += 1
                }
                getStringDay(lastDate) -> {
                    editInfo(4,d)
                    timestampcount5 += 1
                }
            }
        }
        daysForecastEntities[0].cloudiness /= timestampcount1
        daysForecastEntities[0].windSpeed /= timestampcount1
        daysForecastEntities[0].humidity /= timestampcount1
        daysForecastEntities[0].pressure /= timestampcount1

        daysForecastEntities[1].cloudiness /= timestampcount2
        daysForecastEntities[1].windSpeed /= timestampcount2
        daysForecastEntities[1].humidity /= timestampcount2
        daysForecastEntities[1].pressure /= timestampcount2

        daysForecastEntities[2].cloudiness /= timestampcount3
        daysForecastEntities[2].windSpeed /= timestampcount3
        daysForecastEntities[2].humidity /= timestampcount3
        daysForecastEntities[2].pressure /= timestampcount3

        daysForecastEntities[3].cloudiness /= timestampcount4
        daysForecastEntities[3].windSpeed /= timestampcount4
        daysForecastEntities[3].humidity /= timestampcount4
        daysForecastEntities[3].pressure /= timestampcount4

        daysForecastEntities[4].cloudiness /= timestampcount5
        daysForecastEntities[4].windSpeed /= timestampcount5
        daysForecastEntities[4].humidity /= timestampcount5
        daysForecastEntities[4].pressure /= timestampcount5

        return daysForecastEntities
    }



    private fun getDate(currentDateUnix:Long, dayOrder: Int): Date {
        val MILLIS_IN_A_DAY:Long = 1000 * 60 * 60 * 24
        val currentDate = Date(currentDateUnix)
        return Date(currentDate.time + MILLIS_IN_A_DAY*(dayOrder-1))
    }
    private fun getStringDay(dt: Date) : String{
        return DateFormat.getDateInstance(DateFormat.LONG).format(dt)
    }
}