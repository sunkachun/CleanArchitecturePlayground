package com.example.presentation.todolist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.note.usecase.GetToDoItem
import com.example.domain.note.usecase.UpdateToDoItem
import com.example.presentation.todolist.model.ToDoDisplayItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ToDoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getToDoItem: GetToDoItem,
    private val updateToDoItem: UpdateToDoItem,
    private val mapper: ToDoDisplayMapper,
) : ViewModel() {

    init {
        fetchToDoItem()
    }

    private val _toDoItem = MutableStateFlow<ToDoDisplayItem?>(null)
    val toDoItem: StateFlow<ToDoDisplayItem?> get() = _toDoItem


    private var getToDoItemsJob: Job? = null

    fun onUpdate(item: ToDoDisplayItem) {
        viewModelScope.launch(Dispatchers.IO) {
            updateToDoItem(mapper.toDomainItem(item))
        }
    }

    private fun fetchToDoItem() {
        val id = savedStateHandle.get<String>("itemId")
        id?.let { id ->
            getToDoItemsJob?.cancel()
            getToDoItemsJob = viewModelScope.launch(Dispatchers.IO) {
                val toDoItem = getToDoItem(id.toInt())
                withContext(Dispatchers.Main) {
                    _toDoItem.value = toDoItem?.let { mapper.toDisplayItem(it) }
                }
            }
        }
    }

    fun updateTitle(newTitle: String) {
        _toDoItem.value = _toDoItem.value?.copy(title = newTitle)
    }

    fun updateDescription(newDescription: String) {
        _toDoItem.value = _toDoItem.value?.copy(description = newDescription)
    }

    fun updateCompleted(isCompleted: Boolean) {
        _toDoItem.value = _toDoItem.value?.copy(completed = isCompleted)
    }
}