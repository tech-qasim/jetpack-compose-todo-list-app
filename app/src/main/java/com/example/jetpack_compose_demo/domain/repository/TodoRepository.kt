package com.example.jetpack_compose_demo.domain.repository

import com.example.jetpack_compose_demo.data.local.entity.TodoEntity
import com.example.jetpack_compose_demo.domain.model.Todo

interface TodoRepository {
    suspend fun getTodos() : List<Todo>
    suspend fun addTodo(title: String, desc: String) : Todo
    suspend fun updateTodo (todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}