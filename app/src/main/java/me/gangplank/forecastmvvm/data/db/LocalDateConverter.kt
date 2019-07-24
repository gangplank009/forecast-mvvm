package me.gangplank.forecastmvvm.data.db

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(string: String?): LocalDate? = string?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(localDate: LocalDate?): String? = localDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)
}