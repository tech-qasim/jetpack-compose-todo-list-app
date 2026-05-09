package com.example.jetpack_compose_demo.feature

import TodoIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_compose_demo.domain.model.Todo


sealed class Screen(val route: String) {
    object List : Screen("list")
    object Add : Screen("add")
}

@Composable
fun TodoNav  ( viewModel: TodoViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            TodoScreen (
                viewModel = viewModel,
                onAddClick = { navController.navigate(Screen.Add.route) }
            )
        }
        composable(Screen.Add.route) {
            AddTodoScreen (
              viewModel= viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    onAddClick: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text("Todo App", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(state.value.todos.size) { index ->

                    TodoItem(
                        todo = state.value.todos[index],
                        onDelete = {
                            viewModel.handleIntent(TodoIntent.DeleteTodo(state.value.todos[index]))
                        },
                        onToggle = {
                            viewModel.handleIntent(TodoIntent.ToggleTodo(state.value.todos[index]))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddTodoScreen(
    viewModel: TodoViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Add Todo", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                viewModel.handleIntent(TodoIntent.AddTodo(title, description))
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Todo")
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(todo.title)
            Text(todo.description)
        }

        Row {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { onToggle() }
            )

            TextButton(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}