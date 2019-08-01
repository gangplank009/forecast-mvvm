package me.gangplank.forecastmvvm.data.db.unitslocalized.future.list

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class MetricsSimpleFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry {

}