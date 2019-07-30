package me.gangplank.forecastmvvm.ui.weather.future.list

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import me.gangplank.forecastmvvm.R
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.UnitSpecificSimpleFutureWeatherEntry
import me.gangplank.forecastmvvm.ui.base.ScopedFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@RequiresApi(Build.VERSION_CODES.O)
class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()
    private lateinit var viewModelList: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelList = ViewModelProviders.of(this, viewModelFactory)
            .get(FutureListWeatherViewModel::class.java)
        bindUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModelList.weatherEntries.await()
        val futureWeatherLocation = viewModelList.weatherLocation.await()

        futureWeatherLocation.observe(this@FutureListWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        futureWeatherEntries.observe(this@FutureListWeatherFragment, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToNextWeek()

            initRecyclerView(weatherEntries.toFutureWeatherItems())
        })
    }

    private fun updateLocation(name: String) {
        (activity as AppCompatActivity).supportActionBar?.title = name
    }

    private fun updateDateToNextWeek() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Next Week"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun List<UnitSpecificSimpleFutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it)
        }
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this@FutureListWeatherFragment.context, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
