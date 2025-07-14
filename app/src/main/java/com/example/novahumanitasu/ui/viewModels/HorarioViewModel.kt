package com.example.novahumanitasu.ui.viewModels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novahumanitasu.model.entities.HorarioEntity
import com.example.novahumanitasu.model.entities.CursoEntity // Aún necesitamos esta importación sigetAllCursos() devuelve CursoEntity
import com.example.novahumanitasu.repository.HorarioRepository // El repositorio dedicado para Horarios
import com.example.novahumanitasu.repository.CursoRepository // <--- AÚN ES NECESARIO para getAllCursos()
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import java.time.Month // <--- Puedes importar java.time.Month para mayor legibilidad
import kotlinx.coroutines.launch

@HiltViewModel
class HorarioViewModel @Inject constructor(
    private val horarioRepository: HorarioRepository, // Inyecta el HorarioRepository
    private val cursoRepository: CursoRepository, // <-- NUEVO
) : ViewModel() {

    // Método principal para obtener horarios de un curso específico en una fecha dada
    fun getHorariosPorCursoYFecha(codigoCurso: String, fecha: LocalDate): StateFlow<List<HorarioEntity>> {
        Log.d("HorarioViewModel", "Obteniendo horarios para $codigoCurso en $fecha")
        return horarioRepository.obtenerHorariosPorCursoYFecha(codigoCurso, fecha)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }

    // Método para obtener todos los horarios de una fecha (útil para una vista de agenda general)
    fun getHorariosPorFecha(fecha: LocalDate): StateFlow<List<HorarioEntity>> {
        Log.d("HorarioViewModel", "Obteniendo todos los horarios en $fecha")
        return horarioRepository.obtenerHorariosPorFecha(fecha)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }


    init {
        viewModelScope.launch {

            // --- SOLO INSERTAR HORARIOS DE PRUEBA ---
            val today = LocalDate.now()
            val tomorrow = today.plusDays(1)
            val dayAfterTomorrow = today.plusDays(2)
            val fechaExamenFinal = LocalDate.of(2024, Month.DECEMBER, 15) // Año, Mes (Enum), Día
            val aaa = LocalDate.of(2025, 7, 13) // Año, Mes (Int), Día

            val testHorarios = listOf(
                // Horarios para INF123 (asumiendo que INF123 ya existe)
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = aaa,
                    horaInicio = LocalTime.of(9, 0),
                    horaFin = LocalTime.of(10, 30),
                    tipo = "Clase",
                    salon = "C101"
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = today,
                    horaInicio = LocalTime.of(10, 45),
                    horaFin = LocalTime.of(12, 15),
                    tipo = "Laboratorio",
                    salon = "V205"
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = tomorrow,
                    horaInicio = LocalTime.of(14, 0),
                    horaFin = LocalTime.of(15, 30),
                    tipo = "Práctica",
                    salon = "A301"
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = dayAfterTomorrow,
                    horaInicio = LocalTime.of(9, 0),
                    horaFin = LocalTime.of(11, 0),
                    tipo = "Examen",
                    salon = "E202"
                ),

                // Horarios para MAT201 (asumiendo que MAT201 ya existe)
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = today,
                    horaInicio = LocalTime.of(13, 0),
                    horaFin = LocalTime.of(14, 30),
                    tipo = "Clase",
                    salon = "M203"
                ),
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = tomorrow,
                    horaInicio = LocalTime.of(10, 0),
                    horaFin = LocalTime.of(11, 30),
                    tipo = "Clase",
                    salon = "B203"
                ),
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = fechaExamenFinal,
                    horaInicio = LocalTime.of(14, 0),
                    horaFin = LocalTime.of(16, 0),
                    tipo = "Práctica",
                    salon = "B203"
                ),

                // Puedes añadir más horarios para otros cursos si existen
                // HorarioEntity(
                //    codigoCurso = "BIO300", // Asegúrate de que BIO300 exista antes de insertar
                //    fecha = today,
                //    horaInicio = LocalTime.of(16, 0),
                //    horaFin = LocalTime.of(17, 30),
                //    tipo = "Clase",
                //    salon = "Aula D401"
                // ),
            )

            //horarioRepository.insertarHorarios(testHorarios)
            val cursos = cursoRepository.getCursos().first()
            Log.d("HorarioViewModel", "Horarios de prueba insertados.")
            if (cursos.any { it.codigo == "INF123" }) {
                horarioRepository.insertarHorarios(testHorarios)
            } else {
                Log.w("HorarioViewModel", "No se insertaron horarios: cursos aún no creados.")
            }
        }
    }
}