package com.example.openweatherforecast.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentWeather")
data class CurrentWeather (
    @PrimaryKey (autoGenerate = true) val id:Int,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "city") val city: String,
    )