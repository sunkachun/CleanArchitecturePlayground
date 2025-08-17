package com.example.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.note.usecase.DeleteToDoItem
import com.example.domain.note.usecase.GetToDoItems
import com.example.domain.note.usecase.UpdateToDoItem
import com.example.presentation.todolist.model.ToDoDisplayItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val getToDoItems: GetToDoItems,
    private val updateToDoItem: UpdateToDoItem,
    private val deleteToDoItem: DeleteToDoItem,
    private val mapper: ToDoDisplayMapper,
) : ViewModel() {

    init {
        fetchToDoItems()
    }

    private val _toDoItems = MutableStateFlow<List<ToDoDisplayItem>>(emptyList())
    val toDoItems: StateFlow<List<ToDoDisplayItem>> get() = _toDoItems

    private var getToDoItemsJob: Job? = null

    fun onDeleteAction(item: ToDoDisplayItem) {
        viewModelScope.launch(Dispatchers.IO) {
            item.id?.let { deleteToDoItem(it) }
        }
    }

    fun onUpdate(item: ToDoDisplayItem) {
        viewModelScope.launch(Dispatchers.IO) {
            updateToDoItem(mapper.toDomainItem(item))
        }
    }

    private fun fetchToDoItems() {
        getToDoItemsJob?.cancel()
        getToDoItemsJob = getToDoItems()
            .onEach {
                toDoNotes -> _toDoItems.value = toDoNotes.filter { it.id != null }.map(mapper::toDisplayItem)
            }
            .launchIn(viewModelScope)
    }
}