package com.example.todonote.domain.repository

import com.example.todonote.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    fun getAllTodo(): Flow<List<Todo>>

    fun getTodoById(todoId: Int): Flow<Todo>

}