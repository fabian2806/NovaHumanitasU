package com.example.novahumanitasu.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.model.entities.ReminderLogEntity
import com.example.novahumanitasu.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import android.util.Log
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@HiltViewModel
class ReminderLogViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    // Flow que expone todos los recordatorios logueados
    val allReminders: StateFlow<List<ReminderLogEntity>> =
        reminderRepository.getAllReminders()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000), // Mantiene el flow activo mientras haya suscriptores
                emptyList()
            )

    // Un SharedFlow para notificar a la UI sobre nuevos recordatorios que se han logueado
    private val _newReminderEvent = MutableSharedFlow<ReminderLogEntity>()
    val newReminderEvent: SharedFlow<ReminderLogEntity> = _newReminderEvent

    init {
        // --- NUEVA LÓGICA DE INSERCIÓN INICIAL ---
        // Se ejecuta al crear el ViewModel. Ideal para poblar la base de datos con datos de prueba.
        viewModelScope.launch {
            // 1. Comprobamos si la tabla de recordatorios está vacía.
            // .first() toma el primer valor emitido por el Flow y detiene la recolección.
            if (reminderRepository.getAllReminders().first().isEmpty()) {
                Log.d("ReminderLogViewModel", "La tabla 'reminder_logs' está vacía. Insertando dato de prueba.")

                // 2. Creamos la entidad del recordatorio con los datos hardcodeados.
                val testReminder = ReminderLogEntity(
                    horarioCodigoCurso = "MAT201",
                    horarioFecha = LocalDate.of(2025, 7, 11), // Año, Mes, Día
                    horarioHoraInicio = LocalTime.of(16, 0), // Hora, Minuto
                    reminderScheduledTime = LocalDateTime.of(2025, 7, 11, 15, 45), // Hora programada (ej. 15 min antes)
                    reminderMessage = "Recordatorio para tu Practica de Cálculo Diferencial.",
                    timestampLogged = LocalDateTime.now(), // Hora en que se creó el log
                    horarioSalon = "L502",
                    tipoActividad = "Practica"
                )

                // 3. Insertamos el recordatorio a través del repositorio.
                reminderRepository.addReminder(testReminder)
                Log.d("ReminderLogViewModel", "Recordatorio de prueba para MAT201 insertado.")
            } else {
                Log.d("ReminderLogViewModel", "La tabla 'reminder_logs' ya contiene datos. No se inserta nada.")
            }
        }

        // --- Lógica existente para observar nuevos recordatorios ---
        // Observa cambios en la lista de todos los recordatorios.
        viewModelScope.launch {
            var lastEmittedReminder: ReminderLogEntity? = null
            allReminders.collect { reminders ->
                val latestReminder = reminders.firstOrNull() // El más reciente por timestamp_logged DESC

                if (latestReminder != null && latestReminder != lastEmittedReminder) {
                    _newReminderEvent.emit(latestReminder)
                    lastEmittedReminder = latestReminder
                    Log.d("ReminderLogViewModel", "Emitiendo nuevo evento de recordatorio: ${latestReminder.reminderMessage}")
                }
            }
        }
    }
}
