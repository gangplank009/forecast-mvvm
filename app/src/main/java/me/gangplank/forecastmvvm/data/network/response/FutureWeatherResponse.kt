package me.gangplank.forecastmvvm.data.network.response


import com.google.gson.annotations.SerializedName
import me.gangplank.forecastmvvm.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val forecastDaysContainer: ForecastDaysContainer,
    val location: WeatherLocation
)