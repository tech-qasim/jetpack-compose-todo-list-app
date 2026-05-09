package com.example.jetpack_compose_demo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpack_compose_demo.data.local.dao.TodoDao
import com.example.jetpack_compose_demo.data.local.entity.TodoEntity


@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun todoDao(): TodoDao
}