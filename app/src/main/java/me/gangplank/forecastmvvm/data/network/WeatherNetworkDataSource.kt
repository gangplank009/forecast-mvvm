package me.gangplank.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import me.gangplank.forecastmvvm.data.network.response.CurrentWeatherResponse
import me.gangplank.forecastmvvm.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}