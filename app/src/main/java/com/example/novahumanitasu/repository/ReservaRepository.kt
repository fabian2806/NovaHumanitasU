package com.example.novahumanitasu.repository

import com.example.novahumanitasu.model.entities.ReservaEntity
import com.example.novahumanitasu.room.ReservasDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReservaRepository @Inject constructor(
    private val reservasDao: ReservasDao
) {
    fun listarReservas(): Flow<List<ReservaEntity>> = reservasDao.listarReservas()
    
    suspend fun registrarReserva(reserva: ReservaEntity) = reservasDao.registrarReserva(reserva)
    
    suspend fun eliminarReserva(id: Int) = reservasDao.eliminarReserva(id)
    
    fun obtenerReservasPorFecha(fecha: String): Flow<List<ReservaEntity>> = 
        reservasDao.obtenerReservasPorFecha(fecha)
    
    fun obtenerReservasPorLugar(lugar: String): Flow<List<ReservaEntity>> = 
        reservasDao.obtenerReservasPorLugar(lugar)
    
    suspend fun obtenerReservaPorId(id: Int): ReservaEntity? = reservasDao.obtenerReservaPorId(id)
} 