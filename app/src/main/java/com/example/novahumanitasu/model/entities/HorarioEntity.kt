package com.example.novahumanitasu.model.entities
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import java.time.LocalDate // Necesitarás Java 8+ API desugaring o API 26+
import java.time.LocalTime // Necesitarás Java 8+ API desugaring o API 26+

@Entity(
    tableName = "horario",
    primaryKeys = ["codigoCurso", "fecha", "horaInicio"], // Clave primaria compuesta para unicidad
    foreignKeys = [ForeignKey(
        entity = CursoEntity::class,
        parentColumns = ["codigo"],
        childColumns = ["codigoCurso"],
        onDelete = ForeignKey.CASCADE // Si se borra un curso, se borran sus horarios
    )]
)
data class HorarioEntity(
    val codigoCurso: String,
    val fecha: LocalDate, // La fecha de la clase
    val horaInicio: LocalTime, // Hora de inicio de la clase
    val horaFin: LocalTime,    // Hora de fin de la clase
    val tipo: String,          // Ej: "Clase", "Práctica", "Laboratorio", "Examen"
    val salon: String,          // Ej: "V304", "Lab 201"
    val recordatorioActivo: Boolean = false
)