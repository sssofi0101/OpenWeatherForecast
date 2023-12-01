package com.example.openweatherforecast.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openweatherforecast.data.room.entities.DayDetails
import com.example.openweatherforecast.domain.models.DayDetailsEntity

@Dao
interface DayDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDayDetails(dayDetails: DayDetails)

    @Query("DELETE FROM DayDetails")
    fun deleteAllDayDetails()

    @Query("SELECT DayDetails.date,max_gust,avg_visibility,humidity,clouds,pressure, wind_speed," +
            "max_temp,min_temp FROM DayDetails INNER JOIN Days ON DayDetails.date = Days.date WHERE DayDetails.date LIKE :date")
    fun getFullDayInfo(date : String) : DayDetailsEntity

}