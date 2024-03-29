package me.gangplank.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import me.gangplank.forecastmvvm.data.db.entity.WeatherLocation
import me.gangplank.forecastmvvm.data.db.unitslocalized.current.UnitSpecificCurrentWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>
}