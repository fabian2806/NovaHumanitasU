package com.example.novahumanitasu.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reserva")
data class ReservaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hora: String,
    val fecha: String,
    val lugar: String,
    val numeroPersonas: Int
) 