package com.example.presentation.todolist

import com.example.domain.note.model.ToDoNote
import com.example.presentation.todolist.model.ToDoDisplayItem
import timber.log.Timber
import javax.inject.Inject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ToDoDisplayMapper @Inject constructor() {

    fun toDomainItem(toDoDisplayItem: ToDoDisplayItem): ToDoNote = with(toDoDisplayItem) {
        ToDoNote(
            id = id,
            title = title,
            description = description,
            recordTime = recordTime,
            completed = completed
        )
    }

    fun toDisplayItem(toDoNote: ToDoNote): ToDoDisplayItem = with(toDoNote) {
        ToDoDisplayItem(
            id = id,
            title = title,
            description = description,
            recordTime = recordTime,
            displayTime = formatTimestamp(recordTime),
            completed = completed
        )
    }

    private fun formatTimestamp(recordTime: String): String {
        return try {
            val instant = Instant.ofEpochMilli(recordTime.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: NumberFormatException) {
            Timber.e(e)
            recordTime
        }
    }
}