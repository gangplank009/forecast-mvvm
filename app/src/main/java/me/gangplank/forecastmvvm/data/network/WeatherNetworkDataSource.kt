package me.gangplank.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import me.gangplank.forecastmvvm.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String)
}