package com.example.jetpack_compose_demo.data.repository

import com.example.jetpack_compose_demo.data.local.dao.TodoDao
import com.example.jetpack_compose_demo.data.local.entity.TodoEntity
import com.example.jetpack_compose_demo.domain.model.Todo
import com.example.jetpack_compose_demo.domain.repository.TodoRepository

class TodoRepositoryImpl (private val todoDao : TodoDao) : TodoRepository{


    override suspend fun getTodos(): List<Todo> {
        val todoEntities = todoDao.getTodos()
        val todos = mutableListOf<Todo>()

        for (todoEntity in todoEntities) {
            val todo = Todo(
                id = todoEntity.id,
                title = todoEntity.title,
                description = todoEntity.description,
                isDone = todoEntity.isDone
            )

            todos.add(todo)
        }

        return todos
    }


    override suspend fun addTodo(title: String, desc: String): Todo {
        val entity = TodoEntity(
            title = title,
            description = desc,
            isDone = false
        )

        val id = todoDao.insert(entity)

        val todo = Todo(id=id, title=title, description=desc, isDone = false)

        return todo

    }

    override suspend fun updateTodo(todo: Todo) {
        val entity = TodoEntity(title = todo.title, description = todo.description, isDone = todo.isDone)


       todoDao.update(entity)
    }

    override suspend fun deleteTodo(todo: Todo) {
        try {
            val entity = TodoEntity(
                id = todo.id,
                title = todo.title, description = todo.description, isDone = todo.isDone)


            todoDao.delete(entity)
        } catch (e: Exception) {
            print("deletion error : $e")
        }

    }

}