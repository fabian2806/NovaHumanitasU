package com.example.novahumanitasu.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.R
import com.example.novahumanitasu.model.entities.UsuarioEntity
import com.example.novahumanitasu.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _usuarioAutenticado = MutableStateFlow<UsuarioEntity?>(null)
    val usuarioAutenticado: StateFlow<UsuarioEntity?> = _usuarioAutenticado

    fun loginUsuario(email: String, password: String) {
        viewModelScope.launch {
            val usuario = usuarioRepository.autenticar(email, password)
            _usuarioAutenticado.value = usuario
        }
    }

    init {
        viewModelScope.launch {
            usuarioRepository.guardarUsuario(
                UsuarioEntity(
                    codigoNHU = "20210834",
                    nombres = "Fabián",
                    apellidos = "Montenegro",
                    email = "fabianmr2806@hotmail.com",
                    carrera = "Ingeniería Informática",
                    ciclo = 2025,
                    dni = 78790021,
                    password = "usuario123"
                )
            )
        }
    }

}