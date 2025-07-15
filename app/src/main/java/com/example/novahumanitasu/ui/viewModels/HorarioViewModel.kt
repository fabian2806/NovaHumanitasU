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


    // --- NUEVO MÉTODO PARA ACTIVAR/DESACTIVAR EL RECORDATORIO ---
    /**
     * Cambia el estado booleano de 'recordatorioActivo' para un horario específico.
     * Este método se llamará desde la UI (por ejemplo, al pulsar un Switch o un Icono).
     * @param horario El objeto HorarioEntity que se va a actualizar en la base de datos.
     */
    fun toggleRecordatorio(horario: HorarioEntity) {
        viewModelScope.launch {
            // Usamos .copy() para crear un nuevo objeto con el valor invertido.
            // Esto es una buena práctica de inmutabilidad y funciona perfecto con data classes.
            val horarioActualizado = horario.copy(recordatorioActivo = !horario.recordatorioActivo)

            // Llamamos al repositorio para que persista el cambio en la base de datos.
            horarioRepository.actualizarHorario(horarioActualizado)

            Log.d("HorarioViewModel", "Recordatorio para ${horario.codigoCurso} a las ${horario.horaInicio} actualizado a: ${horarioActualizado.recordatorioActivo}")
        }
    }
    init {
        viewModelScope.launch {
            // --- SOLO INSERTAR HORARIOS DE PRUEBA ---
            val today = LocalDate.now()
            val tomorrow = today.plusDays(1)
            val dayAfterTomorrow = today.plusDays(2)
            val fechaExamenFinal = LocalDate.of(2024, Month.DECEMBER, 15)
            val aaa = LocalDate.of(2025, 7, 13)

            // --- ACTUALIZACIÓN: Se añade el campo 'recordatorioActivo' a cada entidad ---
            val testHorarios = listOf(
                // Horarios para INF123
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = aaa,
                    horaInicio = LocalTime.of(9, 0),
                    horaFin = LocalTime.of(10, 30),
                    tipo = "Clase",
                    salon = "C101",
                    recordatorioActivo = false // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = today,
                    horaInicio = LocalTime.of(9, 51),
                    horaFin = LocalTime.of(10, 30),
                    tipo = "Clase",
                    salon = "C102",
                    recordatorioActivo = true // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = today,
                    horaInicio = LocalTime.of(8, 47),
                    horaFin = LocalTime.of(10, 30),
                    tipo = "Laboratorio",
                    salon = "V205",
                    recordatorioActivo = true // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = tomorrow,
                    horaInicio = LocalTime.of(14, 0),
                    horaFin = LocalTime.of(15, 30),
                    tipo = "Práctica",
                    salon = "A301",
                    recordatorioActivo = false // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "INF123",
                    fecha = dayAfterTomorrow,
                    horaInicio = LocalTime.of(9, 0),
                    horaFin = LocalTime.of(11, 0),
                    tipo = "Examen",
                    salon = "E202",
                    recordatorioActivo = true // Ejemplo de un recordatorio activado por defecto
                ),

                // Horarios para MAT201
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = today,
                    horaInicio = LocalTime.of(8, 45),
                    horaFin = LocalTime.of(11, 30),
                    tipo = "Clase",
                    salon = "M203",
                    recordatorioActivo = true // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = tomorrow,
                    horaInicio = LocalTime.of(10, 0),
                    horaFin = LocalTime.of(11, 30),
                    tipo = "Clase",
                    salon = "B203",
                    recordatorioActivo = false // Nuevo campo
                ),
                HorarioEntity(
                    codigoCurso = "MAT201",
                    fecha = fechaExamenFinal,
                    horaInicio = LocalTime.of(14, 0),
                    horaFin = LocalTime.of(16, 0),
                    tipo = "Práctica",
                    salon = "B203",
                    recordatorioActivo = true // Ejemplo de un recordatorio activado por defecto
                ),
            )

            // Esta lógica previene que los horarios se inserten cada vez que el ViewModel se crea.
            // Solo los insertará si la tabla de cursos ya tiene datos (lo cual es una buena señal
            // de que la BD se ha inicializado).
            val cursos = cursoRepository.getCursos().first()
            if (cursos.any { it.codigo == "INF123" ||  it.codigo == "MAT201"}) {
                // Descomenta la siguiente línea solo la primera vez o después de limpiar datos.
                horarioRepository.insertarHorarios(testHorarios)
                Log.d("HorarioViewModel", "Horarios de prueba insertados (o ya existen).")
            } else {
                Log.w("HorarioViewModel", "No se insertaron horarios: cursos de prueba aún no creados.")
            }
        }
    }
}