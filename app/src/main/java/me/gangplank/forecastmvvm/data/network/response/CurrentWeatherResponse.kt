package me.gangplank.forecastmvvm.data.network.response

import com.google.gson.annotations.SerializedName
import me.gangplank.forecastmvvm.data.db.entity.CurrentWeatherEntry
import me.gangplank.forecastmvvm.data.db.entity.Location


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)