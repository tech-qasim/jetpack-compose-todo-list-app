package com.example.jetpack_compose_demo.feature

import TodoIntent
import TodoUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_demo.domain.model.Todo
import com.example.jetpack_compose_demo.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class TodoViewModel @Inject constructor (private val repository: TodoRepository) : ViewModel() {
    private val _state = MutableStateFlow(TodoUiState())
    val state: StateFlow<TodoUiState> = _state


    init {
        getTodos()
    }


    fun handleIntent(intent: TodoIntent) {
        when (intent) {

            is TodoIntent.AddTodo -> {
                val newTodo = Todo(title = intent.title, description = intent.desc)
                val updatedTodos = _state.value.todos + newTodo

                _state.value = _state.value.copy(todos = updatedTodos)

                viewModelScope.launch {
                    repository.addTodo(intent.title, intent.desc)
                }
            }

            is TodoIntent.ToggleTodo -> {

                val updatedTodos = _state.value.todos.map { todo ->
                    if (todo.id == intent.todo.id) {
                        todo.copy(isDone = !todo.isDone)
                    } else {
                        todo
                    }
                }

                _state.value = _state.value.copy(todos = updatedTodos)


                viewModelScope.launch {
                    repository.updateTodo(intent.todo)

                }
            }

            is TodoIntent.DeleteTodo -> {

                // 1. Update UI immediately
                val updatedTodos = _state.value.todos.filter {
                    it.id != intent.todo.id
                }

                _state.value = _state.value.copy(todos = updatedTodos)

                // 2. Then delete from DB
                viewModelScope.launch {
                    repository.deleteTodo(intent.todo)
                }
            }

            is TodoIntent.LoadTodos -> getTodos()
        }

    }


    private fun getTodos () {
        viewModelScope.launch {
            try {
                val todos = repository.getTodos();
                _state.value = _state.value.copy(todos = todos)
            } catch (e: Exception) {
                print(e.toString())
            }
        }


    }


}