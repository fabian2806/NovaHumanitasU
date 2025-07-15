package com.example.novahumanitasu.repository

import com.example.novahumanitasu.room.HorarioDao // Ajusta esta importación si tu HorarioDao no está en 'com.example.novahumanitasu.dao'
import com.example.novahumanitasu.model.entities.HorarioEntity // Importa la entidad de Horario
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate // Para las operaciones de fecha
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // Indica a Hilt que esta clase debe ser un singleton
class HorarioRepository @Inject constructor(
    private val horarioDao: HorarioDao // Hilt inyectará automáticamente una instancia de HorarioDao aquí
) {


    suspend fun insertarHorario(horario: HorarioEntity) {
        horarioDao.insertarHorario(horario)
    }


    suspend fun insertarHorarios(horarios: List<HorarioEntity>) {
        horarioDao.insertarHorarios(horarios)
    }

    fun obtenerHorariosPorFecha(fecha: LocalDate): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorFecha(fecha)
    }


    fun obtenerHorariosPorCursoYFecha(codigoCurso: String, fecha: LocalDate): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorCursoYFecha(codigoCurso, fecha)
    }


    fun obtenerHorariosPorCurso(codigoCurso: String): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosPorCurso(codigoCurso)
    }

    suspend fun actualizarHorario(horario: HorarioEntity) {
        horarioDao.actualizarHorario(horario)
    }

    fun getHorariosActivosParaFecha(fecha: LocalDate): Flow<List<HorarioEntity>> {
        return horarioDao.getHorariosActivosPorFecha(fecha)
    }

    suspend fun hayHorarios(): Boolean {
        return horarioDao.contarHorarios() > 0
    }
}