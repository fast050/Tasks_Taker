package com.example.tasklisttaker.work

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tasklisttaker.utils.constant.Constants
import com.example.tasklisttaker.utils.notification.notify


class TaskReminderWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val description = inputData.getString(Constants.TASK_DESCRIPTION) ?: ""

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable { // Run your task here
            Toast.makeText(context, "Worker Started", Toast.LENGTH_SHORT).show()
        }, 1000)

        notify(
            context = applicationContext,
            title = "Task Reminder",
            description = description
        )

        return Result.success()
    }

}