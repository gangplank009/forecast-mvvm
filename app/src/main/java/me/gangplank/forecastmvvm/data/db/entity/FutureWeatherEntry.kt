package me.gangplank.forecastmvvm.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    val date: String,
    @Embedded
    val day: Day,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)