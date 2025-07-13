package com.example.novahumanitasu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.novahumanitasu.model.entities.CursoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Query("SELECT * FROM curso")
    fun getCursos(): Flow<List<CursoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCurso(curso: CursoEntity)
}