package com.example.novahumanitasu.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.novahumanitasu.model.entities.CursoEntity
import com.example.novahumanitasu.model.entities.NotaEntity
import com.example.novahumanitasu.model.entities.ReservaEntity
import com.example.novahumanitasu.model.entities.UsuarioEntity

@Database(
    entities = [CursoEntity::class, UsuarioEntity::class, NotaEntity::class, ReservaEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cursoDao(): CursoDao
    abstract fun notaDao(): NotaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun reservasDao(): ReservasDao
}