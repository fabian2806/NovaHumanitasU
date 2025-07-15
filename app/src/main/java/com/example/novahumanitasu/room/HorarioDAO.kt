package com.example.novahumanitasu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.novahumanitasu.model.entities.HorarioEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import androidx.room.Update // <-- IMPORTA UPDATE


@Dao
interface HorarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Reemplaza si ya existe un horario con la misma PK
    suspend fun insertarHorario(horario: HorarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarHorarios(horarios: List<HorarioEntity>)

    @Query("SELECT * FROM horario WHERE fecha = :fecha ORDER BY horaInicio ASC")
    fun getHorariosPorFecha(fecha: LocalDate): Flow<List<HorarioEntity>>

    @Query("SELECT * FROM horario WHERE codigoCurso = :codigoCurso AND fecha = :fecha ORDER BY horaInicio ASC")
    fun getHorariosPorCursoYFecha(codigoCurso: String, fecha: LocalDate): Flow<List<HorarioEntity>>

    // Opcional: para obtener todos los horarios de un curso
    @Query("SELECT * FROM horario WHERE codigoCurso = :codigoCurso ORDER BY fecha ASC, horaInicio ASC")
    fun getHorariosPorCurso(codigoCurso: String): Flow<List<HorarioEntity>>

    @Update
    suspend fun actualizarHorario(horario: HorarioEntity)

    // NUEVO: Consulta para obtener horarios con recordatorio activo para una fecha específica
    @Query("SELECT * FROM horario WHERE fecha = :fecha AND recordatorioActivo = 1")
    fun getHorariosActivosPorFecha(fecha: LocalDate): Flow<List<HorarioEntity>>

    // En tu archivo DAO para Horarios (Ej: HorarioDao.kt)
    @Query("SELECT COUNT(*) FROM horario") // "horarios" es el nombre de tu tabla
    suspend fun contarHorarios(): Int
}