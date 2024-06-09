package com.example.todonote.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todonote.core.Utils
import com.example.todonote.domain.model.Todo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    todo: Todo,
    onCheckedChange: (Todo, Boolean) -> Unit,
    onLongClick: (Todo) -> Unit,
    onClick: (Todo) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 7.dp, vertical = 5.dp)
            .combinedClickable(
                onLongClick = { onLongClick(todo) },
                onClick = { onClick(todo) }
            ),
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = Utils.dateTimeFormat(todo.createdTime)!!,
                    textDecoration =if(todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = todo.title,
                    textDecoration =if(todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = todo.description,
                    textDecoration =if(todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.bodyMedium)
            }

            Checkbox(
                modifier = Modifier.size(20.dp),
                checked = todo.isCompleted,
                onCheckedChange = { onCheckedChange(todo, it) }
            )
        }
    }
}
