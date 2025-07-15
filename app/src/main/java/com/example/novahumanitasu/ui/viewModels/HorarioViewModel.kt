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
import com.example.novahumanitasu.R


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
    // --- BLOQUE INIT MODIFICADO PARA CONTROLAR TODA LA CREACIÓN DE DATOS ---
    init {
        viewModelScope.launch {
            // Comprobamos si ya existen cursos para evitar insertar datos duplicados.
            // .first() toma el primer valor del Flow y cancela la recolección.
            if (cursoRepository.getCursos().first().isEmpty()) {
                Log.d("HorarioViewModel", "Base de datos vacía. Creando datos iniciales...")

                // --- PASO 1: CREAR LOS CURSOS PRIMERO ---
                val cursosAInsertar = listOf(
                    CursoEntity(
                        codigo = "INF123",
                        nombre = "Matemática I",
                        profesor = "Kylian Mbappe",
                        imagen = R.drawable.foto_clase_fgm
                    ),
                    CursoEntity(
                        codigo = "MAT201",
                        nombre = "Cálculo Diferencial",
                        profesor = "Albert Einstein",
                        imagen = R.drawable.foto_clase_fgm
                    ),
                    CursoEntity(
                        codigo = "FIL123",
                        nombre = "Filosofia",
                        profesor = "Immanuel kant",
                        imagen = R.drawable.foto_clase_fgm
                    ),
                    CursoEntity(
                        codigo = "INF654",
                        nombre = "Programacion 1",
                        profesor = "Sam Altman",
                        imagen = R.drawable.foto_clase_fgm
                    )
                )
                // Insertamos todos los cursos. Podrías crear un método `insertarCursos` en el repo para esto.
                cursosAInsertar.forEach { cursoRepository.insertarCurso(it) }
                Log.d("HorarioViewModel", "Cursos de prueba insertados.")



                // --- TU LISTA COMPLETA DE HORARIOS ---
                val horariosCompletos = listOf(
                    // --- HORARIOS PARA INF123 ---
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 1), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 3), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 8), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 10), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 15), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 17), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 22), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 24), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 29), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 31), LocalTime.of(9, 0), LocalTime.of(11, 0), "Clase", "C101", false),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 5), LocalTime.of(14, 0), LocalTime.of(16, 0), "Práctica", "L501", true),
                    HorarioEntity("INF123", LocalDate.of(2025, 7, 19), LocalTime.of(14, 0), LocalTime.of(16, 0), "Práctica", "L501", true),
                    // --- HORARIOS PARA MAT201 ---
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 2), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 4), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 9), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 11), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 16), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 18), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 23), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 25), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 30), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "M205", false),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 10), LocalTime.of(16, 0), LocalTime.of(18, 0), "Práctica", "L502", true),
                    HorarioEntity("MAT201", LocalDate.of(2025, 7, 24), LocalTime.of(16, 0), LocalTime.of(18, 0), "Práctica", "L502", true),
                    // --- HORARIOS PARA INF654 ---
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 2), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 4), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 9), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 11), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 16), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 18), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 23), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 25), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 30), LocalTime.of(16, 0), LocalTime.of(18, 0), "Clase", "D404", false),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 8), LocalTime.of(9, 0), LocalTime.of(11, 0), "Práctica", "L601", true),
                    HorarioEntity("INF654", LocalDate.of(2025, 7, 22), LocalTime.of(9, 0), LocalTime.of(11, 0), "Práctica", "L601", true),
                    // --- HORARIOS PARA FIL123 ---
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 5), LocalTime.of(8, 0), LocalTime.of(10, 0), "Clase", "F112", false),
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 12), LocalTime.of(8, 0), LocalTime.of(10, 0), "Clase", "F112", false),
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 19), LocalTime.of(8, 0), LocalTime.of(10, 0), "Clase", "F112", false),
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 26), LocalTime.of(8, 0), LocalTime.of(10, 0), "Clase", "F112", false),
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 1), LocalTime.of(11, 0), LocalTime.of(13, 0), "Práctica", "L503", true),
                    HorarioEntity("FIL123", LocalDate.of(2025, 7, 15), LocalTime.of(11, 0), LocalTime.of(13, 0), "Práctica", "L503", true),


                    HorarioEntity("FIL123", LocalDate.of(2025, 8, 1), LocalTime.of(7, 0), LocalTime.of(10, 0), "Examen", "L503", true),
                    HorarioEntity("INF654", LocalDate.of(2025, 8, 2), LocalTime.of(9, 0), LocalTime.of(12, 0), "Examen", "L503", true),
                    HorarioEntity("FIL123", LocalDate.of(2025, 6, 1), LocalTime.of(11, 0), LocalTime.of(13, 0), "Clase", "L503", false),
                )

                horarioRepository.insertarHorarios(horariosCompletos)
                Log.d("HorarioViewModel", "Horarios de prueba insertados.")

            } else {
                Log.d("HorarioViewModel", "La base de datos ya contiene datos. No se insertan datos de prueba.")
            }
        }
    }
}