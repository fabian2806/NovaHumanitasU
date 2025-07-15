package com.example.novahumanitasu.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.model.entities.ReservaEntity
import com.example.novahumanitasu.repository.ReservaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservaViewModel @Inject constructor(
    private val reservaRepository: ReservaRepository
) : ViewModel() {

    private val _reservas = MutableStateFlow<List<ReservaEntity>>(emptyList())
    val reservas: StateFlow<List<ReservaEntity>> = _reservas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        cargarReservas()
    }

    fun cargarReservas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                reservaRepository.listarReservas().collect { reservas ->
                    _reservas.value = reservas
                    _isLoading.value = false // Apagar loading tras la primera carga
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar reservas: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun registrarReserva(hora: String, fecha: String, lugar: String, numeroPersonas: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val reserva = ReservaEntity(
                    hora = hora,
                    fecha = fecha,
                    lugar = lugar,
                    numeroPersonas = numeroPersonas
                )
                reservaRepository.registrarReserva(reserva)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error al registrar reserva: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminarReserva(id: Int) {
        viewModelScope.launch {
            try {
                reservaRepository.eliminarReserva(id)
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar reserva: ${e.message}"
            }
        }
    }

    fun limpiarError() {
        _errorMessage.value = null
    }
} 