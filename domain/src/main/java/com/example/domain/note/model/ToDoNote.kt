package com.example.domain.note.model

data class ToDoNote(
    val id: Int? = null,
    val title: String,
    val description: String,
    val recordTime: String,
    val completed: Boolean = false,
)