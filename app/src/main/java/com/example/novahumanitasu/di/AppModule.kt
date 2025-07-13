package com.example.novahumanitasu.di

import android.content.Context
import androidx.room.Room
import com.example.novahumanitasu.room.AppDatabase
import com.example.novahumanitasu.room.CursoDao
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
        ).build()
    }

    @Provides
    fun provideCursoDao(db: AppDatabase): CursoDao = db.cursoDao()

}