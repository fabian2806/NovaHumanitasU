package com.example.novahumanitasu.utils


import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Converters { // 'object' para que sea un singleton

    // Convertidor para LocalDate
    @TypeConverter
    @JvmStatic // Usar JvmStatic para que Room pueda acceder al método estáticamente
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE) // Formato "yyyy-MM-dd"
    }

    @TypeConverter
    @JvmStatic
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    // Convertidor para LocalTime
    @TypeConverter
    @JvmStatic
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ISO_LOCAL_TIME) // Formato "HH:mm:ss"
    }

    @TypeConverter
    @JvmStatic
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
    }
}