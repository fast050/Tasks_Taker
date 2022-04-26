package com.example.tasklisttaker.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.tasklisttaker.data.database.TaskDao
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.ui.main.TasksFilter


class TaskRepository(private val dao: TaskDao) {

    private  val TAG = "TaskRepository"

    private val config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        //.setInitialLoadSizeHint(PAGE_INITIAL_LOAD_SIZE_HINT)
        //.setPrefetchDistance(PAGE_PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    //paging source paging
    private fun getTaskListAll(): LiveData<PagedList<Task>> {
        Log.d(TAG, "getTaskListAll: called")
        return LivePagedListBuilder(dao.getListTasksAll(), config).build()
    }

    //paging source paging
    private fun getTaskListActive(): LiveData<PagedList<Task>> {
        Log.d(TAG, "getTaskListActive: called")
        return LivePagedListBuilder(dao.getListTasksActive(), config).build()
    }

    //paging source paging
    private fun getTaskListCompleted(): LiveData<PagedList<Task>> {
        Log.d(TAG, "getTaskListCompleted: called")
        return LivePagedListBuilder(dao.getListTasksCompleted(), config).build()
    }

    //paging source paging3
    fun getTaskListP3(): LiveData<PagingData<Task>> = Pager(config = PagingConfig(
        10, enablePlaceholders = false
    ),
        pagingSourceFactory = { dao.getListTasksP3() }).liveData

    fun getTaskById(taskId:Int) = dao.getTask(taskId)

    suspend fun insertTask(task:Task) = dao.insertTask(task)

    suspend fun updateTask(task:Task) =dao.updateTask(task)

    suspend fun deleteTask(task:Task)=dao.deleteTask(task)

    fun getFilteredTasks(tasksFilter:TasksFilter):LiveData<PagedList<Task>>{
      return  when (tasksFilter) {
            TasksFilter.ACTIVE_TASKS -> {
                getTaskListActive()
            }
            TasksFilter.COMPLETE_TASKS -> {
                getTaskListCompleted()
            }
            else -> {
                getTaskListAll()
            }

        }
    }

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true
    }
}