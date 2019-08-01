package me.gangplank.forecastmvvm.ui.weather.future.detail

import me.gangplank.forecastmvvm.data.provider.UnitProvider
import me.gangplank.forecastmvvm.data.repository.ForecastRepository
import me.gangplank.forecastmvvm.internal.lazyDeferred
import me.gangplank.forecastmvvm.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntry by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}
