package com.example.tasklisttaker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val note: String,
    val isImportant: Boolean,
    val isCompleted: Boolean
)
