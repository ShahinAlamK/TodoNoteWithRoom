package com.example.todonote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todonote.core.Constants.Companion.TODO_TABLE
import com.example.todonote.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM $TODO_TABLE ORDER BY createdTime DESC")
    fun getAllTodo(): Flow<List<Todo>>

    @Query("SELECT * FROM $TODO_TABLE WHERE id = :todoId")
    fun getTodoById(todoId: Int): Flow<Todo>

}