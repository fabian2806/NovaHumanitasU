package com.example.novahumanitasu.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.R
import com.example.novahumanitasu.model.entities.CursoEntity
import com.example.novahumanitasu.repository.CursoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CursoViewModel @Inject constructor(
    private val repository: CursoRepository
) : ViewModel() {

    val cursos: StateFlow<List<CursoEntity>> = repository.getCursos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.insertarCurso(
                CursoEntity(
                    codigo = "INF123",
                    nombre = "Matemática I",
                    profesor = "Kylian Mbappe",
                    imagen = R.drawable.foto_clase_fgm
                )
            )
            repository.insertarCurso(
                CursoEntity(
                    codigo = "MAT201",
                    nombre = "Cálculo Diferencial", // Nombre del nuevo curso
                    profesor = "Albert Einstein", // Profesor del nuevo curso
                    imagen = R.drawable.foto_clase_fgm // Asegúrate de tener este drawable
                )
            )
        }
    }



}

