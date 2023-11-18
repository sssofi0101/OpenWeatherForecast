package com.example.openweatherforecast.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Days")
data class Day (
    @PrimaryKey val date: String,
    @ColumnInfo(name = "max_temp") val max_temp: Double,
    @ColumnInfo(name = "min_temp") val min_temp: Double,
    @ColumnInfo(name = "humidity") val humidity: Double,
    @ColumnInfo(name = "wind_speed") val wind_speed: Double,
    @ColumnInfo(name = "pressure") val pressure: Double,
    @ColumnInfo(name = "clouds") val clouds: Double,
)