package com.example.tasklisttaker.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasklisttaker.R
import com.example.tasklisttaker.application.TasksTakerApplication
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.databinding.ActivityMainBinding
import com.example.tasklisttaker.ui.adapter.TasksAdapter
import com.example.tasklisttaker.ui.add.AddTaskActivity
import com.example.tasklisttaker.ui.setting.SettingsActivity


class TaskListActivity : AppCompatActivity() {
    private lateinit var viewModel: TasksListViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory =
            TasksListViewModel.TasksListViewModelFactory((application as TasksTakerApplication).repository)
        viewModel = ViewModelProvider(this, factory)[TasksListViewModel::class.java]

        val adapter = TasksAdapter {
            updateTask(it)
        }
        binding.TaskListRecyclerView.adapter = adapter
        viewModel.taskList.observe(this) {
            adapter.submitList(it)
        }

        binding.addTaskFloatBtn.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

    }

    private fun updateTask(task: Task?) {
        if (task == null)
            return

        viewModel.updateTask(task)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

//            R.id.filter_tasks_menu_item -> {
//               // selectFilter(item)
//                true
//            }

            R.id.setting_task_menu_item -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }

            R.id.all_tasks_filter_menu -> {
                viewModel.updateFilter(TasksFilter.ALL_TASKS)
               // Log.d("TAG", "selectFilter: all")
                true
            }
            R.id.active_tasks_filter_menu -> {
                viewModel.updateFilter(TasksFilter.ACTIVE_TASKS)
               // Log.d("TAG", "selectFilter: active")
                true
            }

            R.id.complete_tasks_filter_menu -> {
                viewModel.updateFilter(TasksFilter.COMPLETE_TASKS)
               // Log.d("TAG", "selectFilter: complete")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

}