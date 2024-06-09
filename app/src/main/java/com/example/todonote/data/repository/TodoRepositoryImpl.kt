package com.example.todonote.data.repository

import com.example.todonote.data.dao.TodoDao
import com.example.todonote.domain.model.Todo
import com.example.todonote.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val todoDao: TodoDao) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    override suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    override fun getAllTodo(): Flow<List<Todo>> = todoDao.getAllTodo()

    override fun getTodoById(todoId: Int): Flow<Todo> = todoDao.getTodoById(todoId)
}