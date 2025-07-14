package com.example.novahumanitasu.repository

import com.example.novahumanitasu.room.HorarioDao // Ajusta esta importación si tu HorarioDao no está en 'com.example.novahumanitasu.dao'
import com.example.novahumanitasu.model.entities.HorarioEntity // Importa la entidad de Horario
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate // Para las operaciones de fecha
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para gestionar la interacción con los datos de Horarios.
 * Proporciona una abstracción sobre la fuente de datos subyacente (Room HorarioDao en este caso).
 * @property horarioDao El DAO para operaciones relacionadas con HorarioEntity.
 */
@Singleton // Indica a Hilt que esta clase debe ser un singleton
class HorarioRepository @Inject constructor(
    private val horarioDao: HorarioDao // Hilt inyectará automáticamente una instancia de HorarioDao aquí
) {

    /**
     * Inserta un HorarioEntity en la base de datos.
     * Si un horario con la misma clave primaria (codigoCurso, fecha, horaInicio) ya existe, será reemplazado.
     * @param horario El HorarioEntity a insertar.
     */
    suspend fun insertarHorario(horario: HorarioEntity) {
        horarioDao.insertarHorario(horario)
    }

    /**
     * Inserta una lista de HorarioEntity en la base de datos.
     * Si un horario ya existe, será reemplazado.
     * @param horarios La lista de HorarioEntity a insertar.
     */
    suspend fun insertarHorarios(horarios: List<HorarioEntity>) {
        horarioDao.insertarHorarios(horarios)
    }

    /**
     * Obtiene un flujo reactivo de todos los horarios para una fecha específica,
     * ordenados por hora de inicio.
     * @param fecha La fecha para la cual se desean obtener los horarios.
     * @return Un Flow que emite una lista de HorarioEntity.
     */
    fun obtenerHorariosPorFecha(fecha: LocalDate): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorFecha(fecha)
    }

    /**
     * Obtiene un flujo reactivo de los horarios para un curso específico en una fecha dada,
     * ordenados por hora de inicio.
     * @param codigoCurso El código del curso.
     * @param fecha La fecha para la cual se desean obtener los horarios.
     * @return Un Flow que emite una lista de HorarioEntity.
     */
    fun obtenerHorariosPorCursoYFecha(codigoCurso: String, fecha: LocalDate): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorCursoYFecha(codigoCurso, fecha)
    }

    /**
     * Obtiene un flujo reactivo de todos los horarios para un curso específico,
     * ordenados por fecha y luego por hora de inicio.
     * @param codigoCurso El código del curso cuyas notas se desean obtener.
     * @return Un Flow que emite una lista de HorarioEntity.
     */
    fun obtenerHorariosPorCurso(codigoCurso: String): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorCurso(codigoCurso)
    }
}