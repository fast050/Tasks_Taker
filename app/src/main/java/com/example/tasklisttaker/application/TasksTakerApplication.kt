package com.example.tasklisttaker.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.tasklisttaker.R
import com.example.tasklisttaker.data.database.TaskDatabase
import com.example.tasklisttaker.repository.TaskRepository

class TasksTakerApplication : Application() {

    val repository: TaskRepository by lazy {
        TaskRepository(TaskDatabase.getInstance(this).taskDao())
    }

    override fun onCreate() {
        super.onCreate()

        val shared = getSharedPreferences("Shared pref", MODE_PRIVATE)

        shared.getString(getString(R.string.theme_setting_key), "").apply {

            this?.let {

                when (it) {

                    "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)

                }
            }

        }


    }
}