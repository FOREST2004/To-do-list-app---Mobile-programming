package com.example.todolistapp

import java.util.Date

data class Task(
    val name: String,
    val dueDate: Date,
    val id: Long = System.currentTimeMillis() // ID duy nhất cho mỗi nhiệm vụ
)