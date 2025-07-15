package com.example.novahumanitasu.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.model.entities.NotaEntity
import com.example.novahumanitasu.repository.NotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotaViewModel @Inject constructor(
    private val repository: NotaRepository
): ViewModel() {

    fun getNotasPorCurso(codigoCurso: String): StateFlow<List<NotaEntity>> {
        return repository.obtenerNotasPorCurso(codigoCurso)
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    private fun calcularNotaFinal(pc1: Int, pc2: Int, pc3: Int, pc4: Int, ex1: Int, ex2: Int): Double {
        val pc = (pc1 + pc2 + pc3 + pc4)/4.0

        return (4 * pc + 3 * ex1 + 3 * ex2) / 10.0
    }

    fun obtenerNotaFinal(lista: List<NotaEntity>): Double {
        val pc1 = lista.find { it.evaluacion == "PC1" }?.nota ?: 0
        val pc2 = lista.find { it.evaluacion == "PC2" }?.nota ?: 0
        val pc3 = lista.find { it.evaluacion == "PC3" }?.nota ?: 0
        val pc4 = lista.find { it.evaluacion == "PC4" }?.nota ?: 0
        val ex1 = lista.find { it.evaluacion == "EX1" }?.nota ?: 0
        val ex2 = lista.find { it.evaluacion == "EX2" }?.nota ?: 0

        Log.d("MiApp", "Nota Final: ")

        val notaFinal = calcularNotaFinal(pc1, pc2, pc3, pc4, ex1, ex2)
        Log.d("MiApp", "Nota Final: $notaFinal")

        return notaFinal
    }

    init {
        viewModelScope.launch {
            val notas = listOf(
                NotaEntity("INF123", "PC1", 8),
                NotaEntity("INF123", "PC2", 20),
                NotaEntity("INF123", "PC3", 17),
                NotaEntity("INF123", "PC4", 10),
                NotaEntity("INF123", "EX1", 15),
                NotaEntity("INF123", "EX2", 14)
            )
            repository.insertarNotas(notas)
            val notasMat201 = listOf(
                NotaEntity("MAT201", "PC1", 12),
                NotaEntity("MAT201", "PC2", 18),
                NotaEntity("MAT201", "PC3", 15),
                NotaEntity("MAT201", "PC4", 9),
                NotaEntity("MAT201", "EX1", 10),
                NotaEntity("MAT201", "EX2", 16)
            )
            repository.insertarNotas(notasMat201)

            val otrasNotas = listOf(
                NotaEntity("INF654", "PC1", 6),
                NotaEntity("INF654", "PC2", 5),
                NotaEntity("INF654", "PC3", 5),
                NotaEntity("INF654", "PC4", 2),
                NotaEntity("INF654", "EX1", 19),
                NotaEntity("INF654", "EX2", 20)
            )
            repository.insertarNotas(otrasNotas)

            val notas_fil = listOf(
                NotaEntity("FIL123", "PC1", 11),
                NotaEntity("FIL123", "PC2", 8),
                NotaEntity("FIL123", "PC3", 15),
                NotaEntity("FIL123", "PC4", 9),
                NotaEntity("FIL123", "EX1", 12),
                NotaEntity("FIL123", "EX2", 16)
            )
            repository.insertarNotas(notas_fil)
        }
    }

}