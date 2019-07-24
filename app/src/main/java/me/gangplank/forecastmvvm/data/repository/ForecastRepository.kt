package me.gangplank.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import me.gangplank.forecastmvvm.data.db.entity.WeatherLocation
import me.gangplank.forecastmvvm.data.db.unitslocalized.current.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>

}