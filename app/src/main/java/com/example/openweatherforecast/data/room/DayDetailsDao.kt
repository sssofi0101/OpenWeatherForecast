package com.example.openweatherforecast.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DayDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDayDetails(dayDetails: DayDetails)

    @Query("DELETE FROM DayDetails")
    fun deleteAllDayDetails()

    @Query("SELECT * FROM DayDetails INNER JOIN Days ON DayDetails.date LIKE :date")
    fun getFullDayInfo(date : String)

}