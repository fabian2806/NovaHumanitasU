package com.example.novahumanitasu.repository

import com.example.novahumanitasu.model.entities.NotaEntity
import com.example.novahumanitasu.room.NotaDao
import javax.inject.Inject

class NotaRepository @Inject constructor(
    private val notaDao: NotaDao
) {
    fun getNotasPorCurso(codigoCurso: String) = notaDao.getNotasPorCurso(codigoCurso)

    suspend fun insertarNotas(notas: List<NotaEntity>) = notaDao.insertarNota(notas)

    suspend fun borrarNotas(codigoCurso: String) = notaDao.borrarNotasDeCurso(codigoCurso)
}