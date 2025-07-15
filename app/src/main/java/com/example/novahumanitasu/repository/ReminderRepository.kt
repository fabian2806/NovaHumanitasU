package com.example.novahumanitasu.repository


import com.example.novahumanitasu.room.ReminderDao
import com.example.novahumanitasu.model.entities.ReminderLogEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime

@Singleton
class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {
    suspend fun addReminder(reminder: ReminderLogEntity) {
        reminderDao.insertReminder(reminder)
    }

    fun getAllReminders(): Flow<List<ReminderLogEntity>> {
        return reminderDao.getAllReminders()
    }

    suspend fun reminderExists(
        codigoCurso: String,
        fecha: LocalDate,
        horaInicio: LocalTime,
        scheduledTime: LocalDateTime
    ): Boolean {
        return reminderDao.countExistingReminder(codigoCurso, fecha, horaInicio, scheduledTime) > 0
    }
}