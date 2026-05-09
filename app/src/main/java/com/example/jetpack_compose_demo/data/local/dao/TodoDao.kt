package com.example.jetpack_compose_demo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpack_compose_demo.data.local.entity.TodoEntity
import com.example.jetpack_compose_demo.domain.model.Todo


@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    suspend fun getTodos(): List<TodoEntity>

    @Insert
    suspend fun insert(todo: TodoEntity) : Long

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)
}