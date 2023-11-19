package com.example.openweatherforecast.presentation.weather

class MainWeatherState private constructor(val status: Status, val msg: String? = null) {
    companion object {
        val LOADED = MainWeatherState(Status.SUCCESS)
        val LOADING = MainWeatherState(Status.LOADING)
        fun error(msg: String?) = MainWeatherState(Status.FAILED, msg)
    }

    enum class Status {
        LOADING,
        SUCCESS,
        FAILED
    }
}