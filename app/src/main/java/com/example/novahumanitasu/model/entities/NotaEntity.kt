package com.example.novahumanitasu.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "nota",
    foreignKeys = [ForeignKey(
        entity = CursoEntity::class,
        parentColumns = ["codigo"],
        childColumns = ["codigoCurso"],
        onDelete = CASCADE
    )])
data class NotaEntity(
    @PrimaryKey
    val codigoCurso: String,
    val evaluacion: String, // PC1, PC2, EX1, etc.
    val nota: Int
)
