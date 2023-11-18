package com.example.openweatherforecast.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllDays(vararg days: Day)

    @Query("SELECT * FROM Days")
    fun selectAllDays() : List<Day>

    @Query("DELETE FROM Days")
    fun deleteAllDays()

}