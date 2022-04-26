package com.example.tasklisttaker.ui.adapter

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklisttaker.R
import com.example.tasklisttaker.customview.TaskNoteView
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.ui.detail.DetailTaskActivity
import com.example.tasklisttaker.ui.detail.DetailTaskActivity.Companion.Task_ID
import com.example.tasklisttaker.ui.main.TaskNoteState


class TasksAdapter(
    private val onCheckBoxClicked: (Task) -> Unit
) : PagedListAdapter<Task, TasksAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position) as Task
        holder.bind(task)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTitleTextView: TaskNoteView = itemView.findViewById(R.id.task_note_detail)
        private val isCompleteCheckBox: CheckBox = itemView.findViewById(R.id.task_completed)
        private val taskImportanceImage: ImageView = itemView.findViewById(R.id.task_importance)


        fun bind(task: Task) {

            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context, DetailTaskActivity::class.java)
                detailIntent.putExtra(Task_ID, task.id)
                itemView.context.startActivity(detailIntent)

            }
            isCompleteCheckBox.setOnClickListener {
                Toast.makeText(itemView.context, "click", Toast.LENGTH_SHORT).show()
                onCheckBoxClicked(task.copy(isCompleted = !task.isCompleted))
            }

            taskTitleTextView.text = task.note
            isCompleteCheckBox.isChecked = task.isCompleted
           // taskImportanceImage.isVisible = task.isImportant


            val importantIcon= if (task.isImportant)R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_outline_24
            taskImportanceImage.setImageResource(importantIcon)


            val taskNoteState = if (task.isCompleted) TaskNoteState.COMPLETE else TaskNoteState.INCOMPLETE
            taskTitleTextView.setState(taskNoteState)

        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }

    }
}