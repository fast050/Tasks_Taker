package com.example.tasklisttaker.ui.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasklisttaker.R
import com.example.tasklisttaker.application.TasksTakerApplication
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.databinding.ActivityAddTaskBinding
import com.example.tasklisttaker.ui.main.TasksListViewModel

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: TaskAddViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory =
            TaskAddViewModel.TaskAddViewModelFactory((application as TasksTakerApplication).repository)
        viewModel = ViewModelProvider(this, factory)[TaskAddViewModel::class.java]

        binding.apply {
          saveTaskFloatBtn.setOnClickListener {
              val isCompleted = taskCompletedSwitchDetail.isChecked
              val isImportance = importanceTaskSwtichDetail.isChecked
              val note = taskNoteDetail.text.toString().trim()

              val task = Task(isCompleted = isCompleted, isImportant = isImportance, note = note)

              if (task.note.isBlank() || task.note.isEmpty()) {
                  Toast.makeText(this@AddTaskActivity, "please add note", Toast.LENGTH_SHORT).show()
                  return@setOnClickListener
              }

              viewModel.insertTask(task)
              finish()
          }
        }
    }
}