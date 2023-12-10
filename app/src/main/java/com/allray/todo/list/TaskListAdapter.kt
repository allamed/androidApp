package com.allray.todo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.allray.todo.R
import com.allray.todo.Task

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        // Compare the contents of the Tasks (this will check all fields in your data class).
        return oldItem == newItem
    }
}
class TaskListAdapter : ListAdapter <Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback) {



    // on utilise `inner` ici afin d'avoir accès aux propriétés de l'adapter directement
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.task_description) // Replace with your actual TextView ID for description

        fun bind(task: Task) {
            titleTextView.text = task.title
            descriptionTextView.text=task.description


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

   /* override fun getItemCount(): Int {
        return currentList.size
    }*/

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}