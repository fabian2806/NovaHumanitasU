package com.example.novahumanitasu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.novahumanitasu.model.entities.ReminderLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderLogEntity)

    @Query("SELECT * FROM reminder_logs ORDER BY timestamp_logged DESC")
    fun getAllReminders(): Flow<List<ReminderLogEntity>>

    // Útil para evitar duplicados en el worker, usando los mismos parámetros que definen un horario.
    @Query("""
        SELECT COUNT(*)
        FROM reminder_logs
        WHERE horarioCodigoCurso = :codigoCurso
          AND horarioFecha = :fecha
          AND horarioHoraInicio = :horaInicio
          AND reminderScheduledTime = :scheduledTime
    """)
    suspend fun countExistingReminder(
        codigoCurso: String,
        fecha: LocalDate,
        horaInicio: LocalTime,
        scheduledTime: LocalDateTime
    ): Int
}