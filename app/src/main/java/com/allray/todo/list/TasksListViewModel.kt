package com.allray.todo.list



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allray.todo.Task
import com.allray.todo.data.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TasksListViewModel : ViewModel() {
    private val webService = Api.tasksWebService

    val tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())

    fun refresh() {
        viewModelScope.launch {
            val response = webService.fetchTasks()
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.message()}")
                return@launch
            }
            val fetchedTasks = response.body()!!
            tasksStateFlow.value = fetchedTasks
        }
    }

    fun create(task: Task) {
        // Optimistically add the task to the UI
        val optimisticTasks = tasksStateFlow.value + task
        tasksStateFlow.value = optimisticTasks

        viewModelScope.launch {
            val response = webService.create(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")

                // Revert to the previous state if the network call fails
                tasksStateFlow.value = tasksStateFlow.value.filter { it.id != task.id }
                return@launch
            }

            val createdTask = response.body()!!

            // Merge the response with the current state
            tasksStateFlow.value = optimisticTasks.map {
                if (it.id == createdTask.id) createdTask else it
            }.ifEmpty { listOf(createdTask) }
        }
    }


    fun update(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            tasksStateFlow.value = tasksStateFlow.value.map { if (it.id == updatedTask.id) updatedTask else it }
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }
            tasksStateFlow.value = tasksStateFlow.value.filter { it.id != task.id }

        }
    }
}