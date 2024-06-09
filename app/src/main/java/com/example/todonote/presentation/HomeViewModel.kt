package com.example.todonote.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonote.domain.model.Todo
import com.example.todonote.domain.repository.TodoRepository
import com.example.todonote.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    val homeState: StateFlow<HomeState> = todoRepository.getAllTodo().map {
        HomeState(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeState(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    fun updateComplete(id: Int, isComplete: Boolean) {
        viewModelScope.launch {
            val updatedTodo = homeState.value.data.find { it.id == id }?.copy(isCompleted = isComplete)
            updatedTodo?.let {
                todoRepository.updateTodo(it)
            }
        }
    }

    var addTodoState by mutableStateOf(AddTodoState())
        private set

    fun updateUiState(todoDetails: TodoDetails) {
        addTodoState = AddTodoState(todoDetails = todoDetails, isValidate = validate(todoDetails))
    }

    private fun validate(todoDetails: TodoDetails = addTodoState.todoDetails): Boolean {
        return with(todoDetails) {
            todoDetails.title.isNotEmpty() && todoDetails.description.isNotEmpty() && todoDetails.priority.isNotEmpty()
        }
    }

    suspend fun savedTodo() {
        if (validate()) {
            todoRepository.insertTodo(addTodoState.todoDetails.toTodo())
        }
    }

    var data by mutableStateOf<Todo?>(null)
    fun deleteId(todo: Todo) {
        data = todo
    }

    fun deleteTodo() {
        viewModelScope.launch {
            todoRepository.deleteTodo(data!!)
        }
    }

}

data class HomeState(
    val data: List<Todo> = listOf()
)


fun TodoDetails.toTodo() = Todo(
    id = id,
    title = title,
    description = description,
    priority = priority,
    isCompleted = isCompleted,
)

fun Todo.toTodoDetails() = TodoDetails(
    id = id,
    title = title,
    description = description,
    priority = priority,
    isCompleted = isCompleted,
)

data class AddTodoState(
    val todoDetails: TodoDetails = TodoDetails(),
    val isValidate: Boolean = false
)


data class TodoDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: String = "",
    val isCompleted: Boolean = false,
    val createdTime: Long = System.currentTimeMillis()
)

fun Todo.toTodoUiState(): AddTodoState = AddTodoState(
    todoDetails = this.toTodoDetails(),
    isValidate = true
)
