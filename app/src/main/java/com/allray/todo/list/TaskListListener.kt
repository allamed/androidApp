package com.allray.todo.list

import com.allray.todo.Task

interface TaskListListener {
    fun onClickDelete(task: Task)
    fun onClickEdit(task: Task)
    fun onLongClick(task: Task)
}