package com.example.tasklisttaker.ui.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasklisttaker.R
import com.example.tasklisttaker.application.TasksTakerApplication
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.databinding.ActivityDetailTaskBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModelTaskDetail: TaskDetailViewModel
    private lateinit var viewModelTaskReminder: TaskReminderViewModel
    private lateinit var binding: ActivityDetailTaskBinding
    private var task: Task? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerForContextMenu(binding.taskNoteDetail)

        val viewModelFactory =
            TaskDetailViewModelFactory((application as TasksTakerApplication).repository)
        viewModelTaskDetail =
            ViewModelProvider(owner = this, viewModelFactory)[TaskDetailViewModel::class.java]

        viewModelTaskReminder = ViewModelProvider.AndroidViewModelFactory(application)
            .create(TaskReminderViewModel::class.java)


        val taskId = intent.extras?.getInt(Task_ID) ?: return

        viewModelTaskDetail.setTaskId(taskId)

        viewModelTaskDetail.taskById.observe(this) { taskDetail ->

            if (taskDetail == null)
                return@observe

            task = taskDetail
            binding.importanceTaskSwtichDetail.isChecked = taskDetail.isImportant
            binding.taskCompletedSwitchDetail.isChecked = taskDetail.isCompleted
            binding.taskNoteDetail.setText(taskDetail.note)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.delete_task_menu_item -> {
                showDeleteAlertDialog()
                return true
            }
            R.id.update_task_menu_item -> {
                showUpdateAlertDialog()
                return true
            }
            R.id.reminder_task_menu_item -> {
                showDatePicker()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun showDeleteAlertDialog() {
        AlertDialog.Builder(this).setTitle("Alert")
            .setMessage("Click OK to continue, or Cancel to stop:")
            .setPositiveButton("OK"
            ) { dialog, which -> // User clicked OK button.
                viewModelTaskDetail.deleteTask(task)
                finish()
            }
            .setNegativeButton("Cancel") { dialog, which -> // User cancelled the dialog.
                Toast.makeText(
                    applicationContext, "Task Delete Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }
    private fun showUpdateAlertDialog() {
        AlertDialog.Builder(this).setTitle("Alert")
            .setMessage("Click OK to continue, or Cancel to stop:")
            .setPositiveButton("OK"
            ) { dialog, which -> // User clicked OK button.
                updateTask()
                finish()
            }
            .setNegativeButton("Cancel") { dialog, which -> // User cancelled the dialog.
                Toast.makeText(
                    applicationContext, "Task Update Cancel",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }


    private fun updateTask() {
        task = task?.copy(
            isImportant = binding.importanceTaskSwtichDetail.isChecked,
            isCompleted = binding.taskCompletedSwitchDetail.isChecked,
            note = binding.taskNoteDetail.text.toString().trim()
        )

        viewModelTaskDetail.updateTask(task)
    }


    private fun showDatePicker() {
        // Makes only dates from today forward selectable.
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        //data picker builder
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        //show picker builder
        datePicker.show(this.supportFragmentManager, "tag");

        datePicker.addOnPositiveButtonClickListener {
            // Respond to positive button click.

            // from selection
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            //to get year,month,day from the date picker
            calendar.time = Date(it)

            showTimePicker(
                year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH),
                day = calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        datePicker.addOnNegativeButtonClickListener {
        }

    }

    private fun showTimePicker(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()

        val picker =
            MaterialTimePicker.Builder()
                .setInputMode(INPUT_MODE_KEYBOARD)
                .setTitleText("Select Appointment time")
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(calendar.get(Calendar.HOUR))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()

        picker.show(this.supportFragmentManager, "tag")


        picker.addOnPositiveButtonClickListener {
            val calendarInMil = Calendar.getInstance().apply {
                this.set(Calendar.YEAR, year)
                this.set(Calendar.MONTH, month)
                this.set(Calendar.DAY_OF_MONTH, day)
                this.set(Calendar.HOUR, picker.hour)
                this.set(Calendar.MINUTE, picker.minute)
                this.set(Calendar.SECOND, 0)
            }.timeInMillis

            // val timeSelected = DateFormat.getDateTimeInstance().format(calendarInMil)

            viewModelTaskReminder.startTaskReminder(calendarInMil, task)

            Toast.makeText(this, "set time done", Toast.LENGTH_SHORT).show()
        }
        picker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "see ya another time", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val Task_ID = "Task_ID"
    }

}