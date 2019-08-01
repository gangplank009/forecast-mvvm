package me.gangplank.forecastmvvm.ui.weather.future.list

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import me.gangplank.forecastmvvm.R
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import me.gangplank.forecastmvvm.data.db.unitslocalized.future.list.MetricsSimpleFutureWeatherEntry
import me.gangplank.forecastmvvm.internal.glide.GlideApp
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

@RequiresApi(Build.VERSION_CODES.O)
class FutureWeatherItem(val weatherEntry: UnitSpecificSimpleFutureWeatherEntry): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            val textViewCondition = this.itemView.findViewById<TextView>(R.id.textView_condition)
            textViewCondition.text = weatherEntry.conditionText

            val textViewDate = this.itemView.findViewById<TextView>(R.id.textView_date)
            textViewDate.text = formatDateToString(weatherEntry.date)

            val textViewTemperature = this.itemView.findViewById<TextView>(R.id.textView_temperature)
            val unitAbbreviation = if  (weatherEntry is MetricsSimpleFutureWeatherEntry) "\u00B0C"
            else "\u00B0F"
            textViewTemperature.text = "${weatherEntry.avgTemperature}$unitAbbreviation"

            val imageViewCondition = this.itemView.findViewById<ImageView>(R.id.imageView_condition_icon)
            GlideApp.with(this.containerView)
                .load("https:" + weatherEntry.conditionIconUrl)
                .into(imageViewCondition)
        }
    }

    override fun getLayout(): Int {
        return R.layout.future_list_item
    }


    private fun formatDateToString(date: LocalDate): String {
        val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        return date.format(dtFormatter)
    }
}