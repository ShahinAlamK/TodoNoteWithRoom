package com.example.todonote.di

import android.content.Context
import androidx.room.Room
import com.example.todonote.core.Constants
import com.example.todonote.data.dao.TodoDao
import com.example.todonote.data.network.TodoDatabase
import com.example.todonote.data.repository.TodoRepositoryImpl
import com.example.todonote.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, Constants.DB).build()
    }

    @Provides
    @Singleton
    fun providesTodoDao(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun providesTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }

}