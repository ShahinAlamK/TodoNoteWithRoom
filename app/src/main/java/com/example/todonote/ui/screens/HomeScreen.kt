package com.example.todonote.ui.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todonote.presentation.HomeViewModel
import com.example.todonote.presentation.UpdateViewModel
import com.example.todonote.ui.components.AddDialog
import com.example.todonote.ui.components.EmptyScreen
import com.example.todonote.ui.components.ItemCard
import com.example.todonote.ui.components.OptionDialog
import com.example.todonote.ui.components.UpdateDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (id: Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    updateViewModel: UpdateViewModel = hiltViewModel()
) {

    val uiState by homeViewModel.homeState.collectAsState()

    var updateDialog by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var optionDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    UpdateDialog(
        isOpen = updateDialog,
        onDismissRequest = { updateDialog = false },
        confirmButton = {
            coroutineScope.launch {
                updateViewModel.savedTodo()
            }
            updateDialog = false
        },
        dismissButton = { updateDialog = false })

    OptionDialog(
        isOpen = optionDialog,
        onDismiss = { optionDialog = false },
        complete = {
            updateDialog = false
            optionDialog = false
        },
        edit = {
            updateDialog = true
            optionDialog = false
        },
        delete = {
            homeViewModel.deleteTodo()
            optionDialog = false
        }
    )

    AddDialog(
        isOpen = showDialog,
        onDismissRequest = { showDialog = false },
        confirmButton = {
            showDialog = false
            coroutineScope.launch { homeViewModel.savedTodo() }
        },
        dismissButton = { showDialog = false })


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Todo Note") }, actions = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            })
        }

    ) { paddingValues ->
        if (uiState.data.isEmpty()) {
            EmptyScreen(paddingValues = paddingValues)
        }
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            items(uiState.data) { todo ->
                ItemCard(
                    todo = todo,
                    onCheckedChange = { id, isChecked ->
                        homeViewModel.updateComplete(
                            id = id.id,
                            isComplete = isChecked
                        )
                    },
                    onClick = { navigateToDetail(it.id) },
                    onLongClick = {
                        homeViewModel.deleteId(it)
                        updateViewModel.setUpdateId(it)
                        optionDialog = true
                    }
                )
            }
        }
    }
}

