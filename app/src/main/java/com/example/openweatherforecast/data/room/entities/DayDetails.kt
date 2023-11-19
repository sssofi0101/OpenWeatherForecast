package com.example.openweatherforecast.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "DayDetails")

data class DayDetails (
    @PrimaryKey var date:String,
    @ColumnInfo(name = "max_gust") var max_gust: Long,
    @ColumnInfo(name = "avg_visibility") var avg_visibility: Long
    )