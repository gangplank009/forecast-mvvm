package me.gangplank.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.gangplank.forecastmvvm.data.db.entity.CURRENT_WEATHER_ID
import me.gangplank.forecastmvvm.data.db.entity.CurrentWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.current.ImperialCurrentWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}