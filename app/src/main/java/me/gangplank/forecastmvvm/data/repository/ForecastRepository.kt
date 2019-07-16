package me.gangplank.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import me.gangplank.forecastmvvm.data.db.unitslocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
}