package com.example.novahumanitasu.workers


import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import com.example.novahumanitasu.model.entities.ReminderLogEntity
import com.example.novahumanitasu.repository.HorarioRepository
import com.example.novahumanitasu.repository.ReminderRepository
import java.time.LocalDate
import java.time.LocalDateTime

import kotlinx.coroutines.flow.firstOrNull
import android.util.Log

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val horarioRepository: HorarioRepository,
    private val reminderRepository: ReminderRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("ReminderWorker", "Ejecutando ReminderWorker...")

        val now = LocalDateTime.now()
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)


        val horariosForToday = horarioRepository.getHorariosActivosParaFecha(today).firstOrNull() ?: emptyList()
        val horariosForTomorrow = horarioRepository.getHorariosActivosParaFecha(tomorrow).firstOrNull() ?: emptyList()

        val allHorarios = (horariosForToday + horariosForTomorrow).distinct()

        Log.d("ReminderWorker", "Horarios activos a revisar: ${allHorarios.size}")

        for (horario in allHorarios) {
            val classStartDateTime = LocalDateTime.of(horario.fecha, horario.horaInicio)
            val reminderTriggerTime = classStartDateTime.minusHours(1)

            val isWithinTriggerWindow = now.isAfter(reminderTriggerTime.minusMinutes(5)) && now.isBefore(classStartDateTime)

            if (isWithinTriggerWindow) {
                val message = "¡Tu ${horario.tipo} de ${horario.codigoCurso} en ${horario.salon} comienza a las ${horario.horaInicio} (en menos de 1 hora)!"

                val reminderAlreadyLogged = reminderRepository.reminderExists(
                    horario.codigoCurso,
                    horario.fecha,
                    horario.horaInicio,
                    reminderTriggerTime
                )

                if (!reminderAlreadyLogged) {
                    val reminderLog = ReminderLogEntity(
                        horarioCodigoCurso = horario.codigoCurso,
                        horarioFecha = horario.fecha,
                        horarioHoraInicio = horario.horaInicio,
                        reminderMessage = message,
                        reminderScheduledTime = reminderTriggerTime,
                        timestampLogged = LocalDateTime.now(),
                        horarioSalon = horario.salon,
                        tipoActividad=horario.tipo
                    )
                    reminderRepository.addReminder(reminderLog)
                    Log.d("ReminderWorker", "Recordatorio LOGUEADO: $message")
                } else {
                    Log.d("ReminderWorker", "Recordatorio ya existe para ${horario.codigoCurso} el ${horario.fecha} a las ${horario.horaInicio}. No se duplica.")
                }
            } else {
                Log.d("ReminderWorker", "Recordatorio para ${horario.codigoCurso} el ${horario.fecha} a las ${horario.horaInicio} (se activa en: ${reminderTriggerTime}) está fuera de la ventana de activación (actual: ${now}).")
            }
        }
        return Result.success()
    }
}