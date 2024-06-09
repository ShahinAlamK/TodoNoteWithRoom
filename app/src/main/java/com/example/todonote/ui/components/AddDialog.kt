package com.example.todonote.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todonote.core.Constants
import com.example.todonote.presentation.HomeViewModel
import com.example.todonote.presentation.TodoDetails
import com.example.todonote.presentation.UpdateViewModel

@Composable
fun AddDialog(
    modifier: Modifier = Modifier, isOpen: Boolean,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    var isExpanded by remember { mutableStateOf(false) }

    if (isOpen)
        AlertDialog(
            shape = MaterialTheme.shapes.small,
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = homeViewModel.addTodoState.isValidate,
                    onClick = confirmButton
                ) { Text(text = Constants.ADD_BUTTON) }
            },
            dismissButton = {
                TextButton(onClick = dismissButton) { Text(text = Constants.DISMISS_BUTTON) }
            },
            title = { Text(text = Constants.ADD_BOOK) },
            text = {
                Column {
                    OutlinedTextField(
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = { Text(text = Constants.TODO_TITLE) },
                        value = homeViewModel.addTodoState.todoDetails.title,
                        onValueChange = {
                            homeViewModel.updateUiState(
                                todoDetails = homeViewModel.addTodoState.todoDetails.copy(title = it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        placeholder = { Text(text = Constants.TODO_DESCRIPTION) },
                        value = homeViewModel.addTodoState.todoDetails.description,
                        onValueChange = {
                            homeViewModel.updateUiState(
                                todoDetails = homeViewModel.addTodoState.todoDetails.copy(
                                    description = it
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Priority(
                        todoDetails = homeViewModel.addTodoState.todoDetails,
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it },
                        onClick = {
                            homeViewModel.updateUiState(
                                todoDetails = homeViewModel.addTodoState.todoDetails.copy(priority = it)
                            )
                            isExpanded = false
                        },
                        onDismissRequest = { isExpanded = false }
                    )

                }
            },
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Priority(
    modifier: Modifier = Modifier,
    todoDetails: TodoDetails,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onClick: (String) -> Unit,
    onDismissRequest: () -> Unit
) {

    val categoryList = listOf("High", "Medium", "Low")
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = { Text(text = "Select Priority") },
            value = todoDetails.priority,
            onValueChange = {}
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.exposedDropdownSize(true)
        ) {
            categoryList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = { onClick(it) },
                )
            }
        }

    }

}