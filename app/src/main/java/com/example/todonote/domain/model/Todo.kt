package com.example.todonote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todonote.core.Constants

@Entity(tableName = Constants.TODO_TABLE)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: String,
    val isCompleted: Boolean = true,
    val createdTime: Long = System.currentTimeMillis()
)
