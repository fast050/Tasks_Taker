package com.example.tasklisttaker.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.utils.constant.Constants
import com.example.tasklisttaker.work.TaskReminderWorker
import java.util.*
import java.util.concurrent.TimeUnit

class TaskReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val workManager = WorkManager.getInstance(application)

    fun startTaskReminder(taskReminderTime: Long, task: Task?) {

        if (task==null)
            return

        val data = Data.Builder().apply {
            putLong(Constants.PICKER_TIME_KEY, taskReminderTime)
            putString(Constants.TASK_DESCRIPTION, task.note)
        }

        val calender = Calendar.getInstance()

        val time =(taskReminderTime - calender.timeInMillis)
        val passTime=  if (time>0)time else 1

        Log.d("TAG", "startTaskReminder  calender: ${calender.timeInMillis}")
        Log.d("TAG", "startTaskReminder  taskTimeReinder: $taskReminderTime")
        Log.d("TAG", "startTaskReminder  time: $time")

        val workRequest = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay( 5 ,TimeUnit.SECONDS)
            .setInputData(data.build()).addTag(Worker_Tag)

        workManager.enqueue(workRequest.build())
    }

    fun cancelTaskReminder() {
        workManager.cancelAllWorkByTag(Worker_Tag)
    }

    companion object {
        private const val Worker_Tag = "TaskReminder_TAG"
    }

}