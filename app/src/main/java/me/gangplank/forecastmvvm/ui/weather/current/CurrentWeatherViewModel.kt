package me.gangplank.forecastmvvm.ui.weather.current

import me.gangplank.forecastmvvm.data.provider.UnitProvider
import me.gangplank.forecastmvvm.data.repository.ForecastRepository
import me.gangplank.forecastmvvm.internal.lazyDeferred
import me.gangplank.forecastmvvm.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {
    private val unitSystem = unitProvider.getUnitSystem() //get from Settings

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}
