package com.example.novahumanitasu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.novahumanitasu.model.entities.NotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM nota WHERE codigoCurso = :codigoCurso")
    fun getNotasPorCurso(codigoCurso: String): Flow<List<NotaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarNota(notas: List<NotaEntity>)

    @Query("DELETE FROM nota WHERE codigoCurso = :codigoCurso")
    suspend fun borrarNotasDeCurso(codigoCurso: String)

    @Query("SELECT * FROM nota WHERE codigoCurso = :codigo")
    fun obtenerNotasPorCurso(codigo: String): Flow<List<NotaEntity>>
}