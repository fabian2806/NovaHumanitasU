package com.example.novahumanitasu.di

import android.content.Context
import androidx.room.Room
import com.example.novahumanitasu.room.AppDatabase
import com.example.novahumanitasu.room.CursoDao
import com.example.novahumanitasu.room.NotaDao
import com.example.novahumanitasu.room.ReservasDao
import com.example.novahumanitasu.room.UsuarioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nova_humanitas_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCursoDao(db: AppDatabase): CursoDao = db.cursoDao()

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    fun provideNotaDao(db: AppDatabase): NotaDao = db.notaDao()
    
    @Provides
    fun provideReservasDao(db: AppDatabase): ReservasDao = db.reservasDao()
}