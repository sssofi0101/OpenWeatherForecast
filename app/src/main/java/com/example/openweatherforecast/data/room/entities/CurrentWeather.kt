package com.example.openweatherforecast.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentWeather")
data class CurrentWeather (
    @PrimaryKey val city: String,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "description") val description: String
    )