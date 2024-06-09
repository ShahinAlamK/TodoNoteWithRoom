package com.example.todonote.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todonote.core.Constants
import com.example.todonote.presentation.HomeViewModel
import com.example.todonote.presentation.TodoDetails
import com.example.todonote.presentation.UpdateViewModel


@Composable
fun UpdateDialog(
    modifier: Modifier = Modifier, isOpen: Boolean,
    onDismissRequest: () -> Unit,
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    updateViewModel: UpdateViewModel = hiltViewModel(),
) {
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        updateViewModel.update()
    }

    if (isOpen)
        AlertDialog(
            shape = MaterialTheme.shapes.small,
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = updateViewModel.todoUiState.isValidate,
                    onClick = confirmButton
                ) { Text(text = Constants.UPDATE_BUTTON) }
            },
            dismissButton = {
                TextButton(onClick = dismissButton) { Text(text = Constants.DISMISS_BUTTON) }
            },
            title = { Text(text = Constants.UPDATE_BOOK_SCREEN) },
            text = {
                Column {
                    OutlinedTextField(
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        placeholder = { Text(text = Constants.TODO_TITLE) },
                        value = updateViewModel.todoUiState.todoDetails.title,
                        onValueChange = {
                            updateViewModel.updateUiState(
                                todoDetails = updateViewModel.todoUiState.todoDetails.copy(title = it)
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
                        value = updateViewModel.todoUiState.todoDetails.description,
                        onValueChange = {
                            updateViewModel.updateUiState(
                                todoDetails = updateViewModel.todoUiState.todoDetails.copy(
                                    description = it
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Priority(
                        todoDetails = updateViewModel.todoUiState.todoDetails,
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it },
                        onClick = {
                            updateViewModel.updateUiState(
                                todoDetails = updateViewModel.todoUiState.todoDetails.copy(priority = it)
                            )
                            isExpanded = false
                        },
                        onDismissRequest = { isExpanded = false }
                    )

                }
            },
        )
}
