package com.example.todonote.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonote.domain.model.Todo
import com.example.todonote.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    private var id by mutableIntStateOf(0)
    fun setUpdateId(todo: Todo) { id = todo.id }

    var todoUiState by mutableStateOf(AddTodoState())


    fun update(){
        viewModelScope.launch {
            todoUiState = todoRepository.getTodoById(id).filterNotNull().first().toTodoUiState()
        }
    }

    fun updateUiState(todoDetails: TodoDetails) {
        todoUiState = AddTodoState(todoDetails = todoDetails, isValidate = validate(todoDetails))
    }

    private fun validate(todoDetails: TodoDetails = todoUiState.todoDetails): Boolean {
        return with(todoDetails) {
            todoDetails.title.isNotEmpty() && todoDetails.description.isNotEmpty() && todoDetails.priority.isNotEmpty()
        }
    }

    suspend fun savedTodo() {
        if (validate()) {
            todoRepository.updateTodo(todoUiState.todoDetails.toTodo())
        }
    }


}