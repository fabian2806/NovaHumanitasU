package com.example.novahumanitasu.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.novahumanitasu.model.entities.CursoEntity

@Database(entities = [CursoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cursoDao(): CursoDao
}