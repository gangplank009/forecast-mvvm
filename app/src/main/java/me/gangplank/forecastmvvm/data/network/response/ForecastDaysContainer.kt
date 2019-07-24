package me.gangplank.forecastmvvm.data.network.response


import com.google.gson.annotations.SerializedName
import me.gangplank.forecastmvvm.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)