package com.example.jetpack_compose_demo.di

import android.app.Application
import androidx.room.Room
import com.example.jetpack_compose_demo.data.local.dao.TodoDao
import com.example.jetpack_compose_demo.data.local.database.AppDatabase
import com.example.jetpack_compose_demo.data.repository.TodoRepositoryImpl
import com.example.jetpack_compose_demo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providerDatabase (app: Application) : AppDatabase {
        val db = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "todo_db"
        ).build()

        return db;
    }


    @Provides
    fun provideDao(db: AppDatabase): TodoDao = db.todoDao()

    @Provides
    @Singleton
    fun provideRepository(dao: TodoDao): TodoRepository =
        TodoRepositoryImpl(dao)






}