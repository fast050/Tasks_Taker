package com.example.tasklisttaker.application

import android.app.Application
import com.example.tasklisttaker.data.database.TaskDatabase
import com.example.tasklisttaker.repository.TaskRepository

class TasksTakerApplication :Application(){

    val repository : TaskRepository by lazy{
       TaskRepository (TaskDatabase.getInstance(this).taskDao())
    }
}