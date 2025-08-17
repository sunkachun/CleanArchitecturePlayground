package com.example.cleanarchitectureplayground.ui.screen.todolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.todolist.ToDoDetailViewModel
import timber.log.Timber

@Composable
fun ToDoListDetailScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    itemId: String?
) {
    val viewModel: ToDoDetailViewModel = hiltViewModel()
    val toDoItem = viewModel.toDoItem.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "To-Do Item Details",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    try {
                        toDoItem?.let { item ->
                            viewModel.onUpdate(
                                item.copy(
                                    title = item.title,
                                    description = item.description,
                                    completed = item.completed
                                )
                            )
                        }
                        navController.popBackStack()
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "done"
                )
            }
        }

        toDoItem?.let { item ->
            OutlinedTextField(
                value = item.title,
                onValueChange = { viewModel.updateTitle(it) },
                label = { Text("New Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = item.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("New Content") },
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = item.completed,
                    onCheckedChange = { viewModel.updateCompleted(it) }
                )
                Text(
                    text = "Completed",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}