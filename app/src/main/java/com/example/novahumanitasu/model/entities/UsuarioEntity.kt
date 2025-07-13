package com.example.novahumanitasu.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
    @PrimaryKey val codigoNHU: String,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val carrera: String,
    val ciclo: Int,
    val dni: Int,
    val password: String
)
