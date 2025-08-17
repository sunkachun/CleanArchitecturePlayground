package com.example.cleanarchitectureplayground.ui.screen.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.todolist.ToDoItemAction
import com.example.presentation.todolist.ToDoListViewModel
import com.example.presentation.todolist.model.ToDoDisplayItem

@Composable
fun ToDoListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,

) {
    val viewModel: ToDoListViewModel = hiltViewModel()
    val toDoItems = viewModel.toDoItems.collectAsState(emptyList()).value
    var taskInput by remember { mutableStateOf("") }
    var taskContentInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
            Text(
                text = "To-Do List",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = taskInput,
                onValueChange = { taskInput = it },
                label = { Text("New Task Name") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (taskInput.isNotBlank()) {
                        val newItem = ToDoDisplayItem(
                            id = null,
                            title = taskInput,
                            description = taskContentInput,
                            recordTime = System.currentTimeMillis().toString(),
                            completed = false,
                            displayTime = null,
                        )
                        viewModel.onUpdate(newItem)
                        taskInput = ""
                        taskContentInput = ""
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Absolute.Left,
        ) {
            OutlinedTextField(
                value = taskContentInput,
                onValueChange = { taskContentInput = it },
                label = { Text("New Task Content") },
                modifier = Modifier.weight(1f),
                maxLines = 3,
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(toDoItems, key = { it.id ?: it.hashCode() }) { item ->
                ToDoItem(
                    item = item,
                    onAction = { action ->
                        when (action) {
                            is ToDoItemAction.DeleteNote -> viewModel.onDeleteAction(item)
                            is ToDoItemAction.UpdateItem -> viewModel.onUpdate(action.note)
                            is ToDoItemAction.NavigateToViewDetail -> {
                                navController.navigate("todo_list_detail/${item.id}")
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ToDoItem(
    item: ToDoDisplayItem,
    onAction: (ToDoItemAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clickable {
                onAction(ToDoItemAction.NavigateToViewDetail(item))
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = item.displayTime ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                Checkbox(
                    checked = item.completed,
                    onCheckedChange = { isChecked ->
                        onAction(
                            ToDoItemAction.UpdateItem(
                                item.copy(recordTime = System.currentTimeMillis().toString(), completed = isChecked)
                            )
                        )
                    }
                )
                IconButton(
                    onClick = { onAction(ToDoItemAction.DeleteNote(item)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task"
                    )
                }
            }
        }
    }
}