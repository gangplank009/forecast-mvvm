package me.gangplank.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel;
import me.gangplank.forecastmvvm.data.repository.ForecastRepository
import me.gangplank.forecastmvvm.internal.UnitSystem
import me.gangplank.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC //get from Settings

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
