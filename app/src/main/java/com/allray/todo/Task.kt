package com.allray.todo
data class Task(
    val id: String,
    val title: String,
    val description: String = "No description provided"
)
