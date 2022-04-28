package com.example.tasklisttaker.ui.detail

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tasklisttaker.data.database.TaskDatabase
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.repository.TaskRepository
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDetailViewModelTest {

    //for liveDate
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var viewModel: TaskDetailViewModel

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries().build()

        database.taskDao().insertTask(Task(1, "run under tree", false, true))
        database.taskDao().insertTask(Task(2, "sleep an hour", true, false))

        val fakeRepository = TaskRepository(database.taskDao())

        viewModel = TaskDetailViewModel(fakeRepository)
    }

    @Test
    fun updateTask() {
     val task =Task(1,"draw a ship",false,true)
        viewModel.taskById.observeForever{}
        viewModel.setTaskId(1)

        viewModel.updateTask(task)

        assertEquals(task,viewModel.taskById.value)
    }

    @Test
    fun deleteTask() {
        val task =Task(1,"draw a ship",false,true)
        viewModel.taskById.observeForever{}
        viewModel.setTaskId(1)

        viewModel.deleteTask(task)

        assertNull("this value should equal null , not existed" ,viewModel.taskById.value)
    }

    @Test
    fun setTaskId() {

        val task = Task(2, "sleep an hour", true, false)

        viewModel.taskById.observeForever{}
        viewModel.setTaskId(2)

        assertEquals(task,viewModel.taskById.value)
    }

    @Test
    fun getTaskById() {

        /*
      /LiveData object, the objects need to be observed in order for changes to be emitted.
      A simple way of doing this is by using the observeForever method.
       Call the observeForever method on the quantity object
       */
        val task = Task(1, "run under tree", false, true)
        viewModel.taskById.observeForever {}
        viewModel.setTaskId(1)

        Assert.assertEquals(task, viewModel.taskById.value)
    }
}