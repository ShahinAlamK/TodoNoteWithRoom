package com.example.todonote.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todonote.data.dao.TodoDao
import com.example.todonote.domain.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}