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
        // Observa cambios en la lista de todos los recordatorios.
        // Cuando un nuevo recordatorio se añade, lo emitimos a través de newReminderEvent.
        viewModelScope.launch {
            // firstOrNull() para el valor inicial, luego collect para escuchar cambios
            var lastEmittedReminder: ReminderLogEntity? = null
            reminderRepository.getAllReminders().collect { reminders ->
                val latestReminder = reminders.firstOrNull() // El más reciente por timestamp_logged DESC

                if (latestReminder != null && latestReminder != lastEmittedReminder) {
                    // Solo emitimos si es un recordatorio realmente nuevo o diferente al último emitido
                    _newReminderEvent.emit(latestReminder)
                    lastEmittedReminder = latestReminder
                    Log.d("ReminderLogViewModel", "Emitiendo nuevo evento de recordatorio: ${latestReminder.reminderMessage}")
                }
            }
        }
    }
}