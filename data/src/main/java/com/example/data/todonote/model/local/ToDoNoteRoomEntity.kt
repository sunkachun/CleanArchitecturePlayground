package com.example.data.todonote.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class ToDoNoteRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val title: String,
    val description:  String,
    val recordTime: String,
    val completed: Boolean,
)
