package com.allray.todo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.allray.todo.R
import com.allray.todo.R.layout.fragment_task_list
import com.allray.todo.Task
import com.allray.todo.databinding.FragmentTaskListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class TaskListFragment : Fragment() {
    private var taskList = listOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter = TaskListAdapter()
    private lateinit var floatingActionButton: FloatingActionButton
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         binding.myRecyclerView.adapter = adapter
        adapter.submitList(taskList)
        binding.floatingActionButton2.setOnClickListener {
            // Create a new task
            val newTask = Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}")
            // Update task list
            taskList = taskList + newTask
            // Refresh adapter
            refreshAdapter(taskList)
        }
    }
    private fun refreshAdapter(newTasks: List<Task>) {
        adapter.submitList(newTasks.toList())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear the binding when the view is destroyed
    }
}