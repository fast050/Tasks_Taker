package com.example.tasklisttaker.ui.detail

import android.util.Log
import androidx.lifecycle.*
import androidx.work.WorkManager
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.repository.TaskRepository
import com.example.tasklisttaker.ui.main.TasksFilter
import com.example.tasklisttaker.ui.main.TasksListViewModel
import kotlinx.coroutines.NonCancellable.isCompleted
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskDetailViewModel(private val repository: TaskRepository) : ViewModel() {

    fun updateTask(task: Task?)=viewModelScope.launch{
        if (task==null)
            return@launch

        if (task.note.isBlank())
            return@launch

           val updatedTask= task.copy(  note = task.note.trim(),
               isImportant = task.isImportant,
               isCompleted = task.isCompleted)

        repository.updateTask(updatedTask)
    }

    fun deleteTask(task: Task?)=viewModelScope.launch {
        if (task==null)
            return@launch

        repository.deleteTask(task)
    }

    private val _taskId = MutableLiveData<Int>()

    fun setTaskId(taskId:Int){
        _taskId.value = taskId
    }


    val taskById = _taskId.switchMap { task_Id->
        repository.getTaskById(task_Id)
    }

}
class TaskDetailViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java))
            return TaskDetailViewModel(repository) as T

        throw IllegalArgumentException("View Model UnKnown")
    }
}


