package com.example.tasklisttaker.data.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.example.tasklisttaker.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTasks(tasks: List<Task>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("select * from task")
    fun getListTasksAll(): DataSource.Factory<Int,Task>

    @Query("select * from task where isCompleted = 0")
    fun getListTasksActive(): DataSource.Factory<Int,Task>

    @Query("select * from task where isCompleted = 1")
    fun getListTasksCompleted(): DataSource.Factory<Int,Task>


    @Query("select * from task")
    fun getListTasksP3(): PagingSource<Int,Task>

    @Query("select * from task where id =:taskId")
    fun getTask(taskId: Int): LiveData<Task>

}