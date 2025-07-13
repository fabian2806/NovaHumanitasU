package com.example.novahumanitasu.repository

import com.example.novahumanitasu.model.entities.CursoEntity
import com.example.novahumanitasu.room.CursoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CursoRepository @Inject constructor(
    private val cursoDao: CursoDao
) {
    fun getCursos(): Flow<List<CursoEntity>> = cursoDao.getCursos()

    suspend fun insertarCurso(curso: CursoEntity) = cursoDao.insertarCurso(curso)
}
