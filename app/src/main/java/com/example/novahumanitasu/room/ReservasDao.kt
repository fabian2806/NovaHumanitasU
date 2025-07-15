package com.example.novahumanitasu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.novahumanitasu.model.entities.ReservaEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface ReservasDao {
    @Query("SELECT * FROM reserva ORDER BY fecha, hora")
    fun listarReservas(): Flow<List<ReservaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrarReserva(reserva: ReservaEntity)

    @Query("DELETE FROM reserva WHERE id = :id")
    suspend fun eliminarReserva(id: Int)
    
    @Query("SELECT * FROM reserva WHERE fecha = :fecha ORDER BY hora")
    fun obtenerReservasPorFecha(fecha: String): Flow<List<ReservaEntity>>
    
    @Query("SELECT * FROM reserva WHERE lugar = :lugar ORDER BY fecha, hora")
    fun obtenerReservasPorLugar(lugar: String): Flow<List<ReservaEntity>>
    
    @Query("SELECT * FROM reserva WHERE id = :id")
    suspend fun obtenerReservaPorId(id: Int): ReservaEntity?
}