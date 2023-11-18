package com.example.openweatherforecast.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity (tableName = "DayDetails", foreignKeys = [ForeignKey(entity = Day::class, parentColumns = ["date"], childColumns = ["date"], onDelete = CASCADE)])
data class DayDetails (
    @PrimaryKey val date:String,
    @ColumnInfo(name = "sunrise") val sunrise: Long,
    @ColumnInfo(name = "sunset") val sunset: Long,
    )