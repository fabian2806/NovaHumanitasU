package com.example.novahumanitasu.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "reminder_logs")
data class ReminderLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val horarioCodigoCurso: String, // Para saber de qué curso es el recordatorio
    val horarioFecha: LocalDate, // Usamos LocalDate directamente gracias a TypeConverters
    val horarioHoraInicio: LocalTime, // Usamos LocalTime directamente
    val reminderMessage: String, // El mensaje que se mostrará
    val reminderScheduledTime: LocalDateTime, // La hora y fecha exacta en que el recordatorio debería haber sido disparado (1 hora antes)
    @ColumnInfo(name = "timestamp_logged") val timestampLogged: LocalDateTime // Cuando se añadió este log
)