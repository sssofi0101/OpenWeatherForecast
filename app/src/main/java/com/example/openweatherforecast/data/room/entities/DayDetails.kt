package com.example.openweatherforecast.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.openweatherforecast.data.room.entities.Day

//@Entity (tableName = "DayDetails", foreignKeys = [ForeignKey(entity = Day::class, parentColumns = ["date"], childColumns = ["date"], onDelete = CASCADE)])
@Entity (tableName = "DayDetails")

data class DayDetails (
    @PrimaryKey var date:String,
    @ColumnInfo(name = "max_gust") var max_gust: Long,
    @ColumnInfo(name = "avg_visibility") var avg_visibility: Long
    )