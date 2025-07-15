package com.example.novahumanitasu.utils


import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime


object Converters {

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

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        // Usa un formato ISO que incluya fecha y hora para precisión
        return dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) // Formato "yyyy-MM-ddTHH:mm:ss"
    }

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }
    }
}