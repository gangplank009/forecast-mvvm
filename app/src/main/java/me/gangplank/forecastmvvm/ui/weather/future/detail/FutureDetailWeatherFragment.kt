package me.gangplank.forecastmvvm.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.gangplank.forecastmvvm.R
import me.gangplank.forecastmvvm.data.db.LocalDateConverter
import me.gangplank.forecastmvvm.internal.DateNotFoundExceptions
import me.gangplank.forecastmvvm.internal.glide.GlideApp
import me.gangplank.forecastmvvm.ui.base.ScopedFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactoryInstaceFactory:
            ((LocalDate) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let {
            FutureDetailWeatherFragmentArgs.fromBundle(it)
        }
        val date = LocalDateConverter.stringToDate(safeArgs?.dateString) ?: throw DateNotFoundExceptions()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstaceFactory(date))
            .get(FutureDetailWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val weatherEntry = viewModel.weatherEntry.await()
        val location = viewModel.weatherLocation.await()

        location.observe(this@FutureDetailWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        weatherEntry.observe(this@FutureDetailWeatherFragment, Observer { weatherEntry ->
            if (weatherEntry == null) return@Observer

            group_loading.visibility = View.GONE
            group_weather.visibility = View.VISIBLE

            updateDate(weatherEntry.date)
            updateTemperature(weatherEntry.avgTemperature, weatherEntry.maxTemperature, weatherEntry.minTemperature)
            updateCondition(weatherEntry.conditionText)
            updatePrecipitation(weatherEntry.totalPrecipitation)
            updateWind(weatherEntry.maxWindSpeed)
            updateVisibility(weatherEntry.avgVisibilityDistance)
            updateUV(weatherEntry.uv)

            GlideApp.with(this@FutureDetailWeatherFragment)
                .load("https:" + weatherEntry.conditionIconUrl)
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit)
            metric
        else
            imperial
    }

    private fun updateLocation(location: String) {
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun updateDate(date: LocalDate) {
        (activity as AppCompatActivity).supportActionBar?.subtitle = date.format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        )
    }

    private fun updateTemperature(temperature: Double, max: Double, min: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_max_temperature.text = "Max: $max$unitAbbreviation"
        textView_min_temperature.text = "Min: $min$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationValue: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationValue $unitAbbreviation"
    }

    private fun updateWind(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kmh", "mph")
        textView_wind.text = "Wind: $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

    private fun updateUV(uv: Double) {
        textView_uv.text = "UV: $uv"
    }
}
