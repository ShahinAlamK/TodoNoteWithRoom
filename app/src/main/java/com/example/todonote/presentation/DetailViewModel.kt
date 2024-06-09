package com.example.todonote.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonote.domain.model.Todo
import com.example.todonote.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val todoId: Int = checkNotNull(savedStateHandle["id"])

    val detailState: StateFlow<TodoDetailsState> =
        todoRepository.getTodoById(15).filterNotNull().map { todo ->
            TodoDetailsState(todo.toTodoDetail())
        }.stateIn(
            scope = viewModelScope,
            initialValue = TodoDetailsState(),
            started = SharingStarted.WhileSubscribed(5000)
        )

}

data class TodoDetailsState(
    val todoDetails: TodoDetails = TodoDetails()
)

fun Todo.toTodoDetail(): TodoDetails = TodoDetails(
    id = id,
    title = title,
    description = description,
    priority = priority,
    isCompleted = isCompleted,
    createdTime = createdTime

)