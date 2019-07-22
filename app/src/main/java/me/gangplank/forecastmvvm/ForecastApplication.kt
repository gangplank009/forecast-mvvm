package me.gangplank.forecastmvvm

import android.app.Application
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import me.gangplank.forecastmvvm.data.db.ForecastDatabase
import me.gangplank.forecastmvvm.data.network.*
import me.gangplank.forecastmvvm.data.provider.LocationProvider
import me.gangplank.forecastmvvm.data.provider.LocationProviderImpl
import me.gangplank.forecastmvvm.data.provider.UnitProvider
import me.gangplank.forecastmvvm.data.provider.UnitProviderImpl
import me.gangplank.forecastmvvm.data.repository.ForecastRepository
import me.gangplank.forecastmvvm.data.repository.ForecastRepositoryImpl
import me.gangplank.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        // bind<ForecastDatabase>() with singleton { ForecastDatabase(instance()) }
        bind() from singleton { ForecastDatabase(instance()) }
        // bind<CurrentWeatherDao>() with singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        // bind<CurrentWeatherViewModelFactory>() with provider { CurrentWeatherViewModelFactory(instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}