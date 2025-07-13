package com.example.novahumanitasu.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curso")
data class CursoEntity(
    @PrimaryKey val codigo: String,
    val nombre: String,
    val profesor: String,
    val imagen: Int
)
