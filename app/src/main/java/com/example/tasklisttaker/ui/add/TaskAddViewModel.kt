package com.example.tasklisttaker.ui.add

import androidx.lifecycle.*
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.repository.TaskRepository
import com.example.tasklisttaker.ui.main.TasksListViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskAddViewModel(private val repository: TaskRepository) : ViewModel() {

    fun insertTask(task: Task)=viewModelScope.launch{
        repository.insertTask(task)
    }

//    fun saveTask(task: Task){
//        if (task.note.isBlank() || task.note.isEmpty())
//            return
//
//        insertTask(task)
//    }

    class TaskAddViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskAddViewModel::class.java))
                return TaskAddViewModel(repository) as T

            throw IllegalArgumentException("View Model UnKnown")
        }
    }


}