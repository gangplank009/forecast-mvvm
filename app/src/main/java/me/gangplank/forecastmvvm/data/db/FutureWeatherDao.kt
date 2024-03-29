package me.gangplank.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.gangplank.forecastmvvm.data.db.entity.FutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.detail.ImperialDetailFutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.detail.MetricDetailFutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.list.ImperialSimpleFutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.list.MetricsSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureWeatherEntry>)

    @Query("SELECT * FROM future_weather WHERE date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate: LocalDate): LiveData<List<MetricsSimpleFutureWeatherEntry>>

    @Query("SELECT * FROM future_weather WHERE date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("SELECT count(id) FROM future_weather WHERE date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("DELETE FROM future_weather WHERE date(date) < date(:fistDateToKeep)")
    fun deleteOldEntries(fistDateToKeep: LocalDate)

    @Query("SELECT * FROM future_weather WHERE date(date) = date(:date)")
    fun getDetailedWeatherByDateMetric(date: LocalDate): LiveData<MetricDetailFutureWeatherEntry>

    @Query("SELECT * FROM future_weather WHERE date(date) = date(:date)")
    fun getDetailedWeatherByDateImperial(date: LocalDate): LiveData<ImperialDetailFutureWeatherEntry>
}