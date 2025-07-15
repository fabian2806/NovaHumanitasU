package com.example.novahumanitasu.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.novahumanitasu.model.entities.CursoEntity
import com.example.novahumanitasu.model.entities.HorarioEntity
import com.example.novahumanitasu.model.entities.NotaEntity
import com.example.novahumanitasu.model.entities.ReservaEntity
import com.example.novahumanitasu.model.entities.UsuarioEntity
import com.example.novahumanitasu.utils.Converters
import androidx.room.TypeConverters


@Database(
    entities = [CursoEntity::class, UsuarioEntity::class, NotaEntity::class, ReservaEntity::class, HorarioEntity::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cursoDao(): CursoDao
    abstract fun notaDao(): NotaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun reservasDao(): ReservasDao
    abstract fun horarioDao(): HorarioDao
}