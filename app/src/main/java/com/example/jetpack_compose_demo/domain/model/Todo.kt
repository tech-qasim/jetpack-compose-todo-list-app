package com.example.jetpack_compose_demo.domain.model

data class Todo(
    val id : Long=0,
    val title: String,
    val description: String,
    val isDone: Boolean=false,
)
