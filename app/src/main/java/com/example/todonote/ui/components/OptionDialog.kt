package com.example.todonote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todonote.presentation.UpdateViewModel

@Composable
fun OptionDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    complete: () -> Unit = {},
    delete: () -> Unit,
    edit: () -> Unit,
    updateViewModel: UpdateViewModel = hiltViewModel()
) {
    updateViewModel.update()

    if (isOpen) Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 20.dp, horizontal = 8.dp)
        ) {

            Surface(
                onClick = complete
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = "Complete Todo", modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }

            Surface(
                onClick = edit
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = "Edit", modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }

            Surface(
                onClick = delete
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(13.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(text = "Delete", modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
                }
            }


        }
    }
}