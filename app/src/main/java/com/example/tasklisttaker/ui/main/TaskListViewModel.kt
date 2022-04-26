package com.example.tasklisttaker.ui.main

import androidx.lifecycle.*
import com.example.tasklisttaker.data.model.Task
import com.example.tasklisttaker.repository.TaskRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

enum class TasksFilter {
    COMPLETE_TASKS, ACTIVE_TASKS, ALL_TASKS
}

enum class TaskNoteState {
    COMPLETE, INCOMPLETE
}

class TasksListViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _filter = MutableLiveData<TasksFilter>()

    init {
        _filter.value = TasksFilter.ALL_TASKS
    }

    val taskList = _filter.switchMap { tasksFilter ->
        repository.getFilteredTasks(tasksFilter)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun updateFilter(filter: TasksFilter) {
        _filter.value = filter
    }

    class TasksListViewModelFactory(private val repository: TaskRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TasksListViewModel::class.java))
                return TasksListViewModel(repository) as T

            throw IllegalArgumentException("View Model UnKnown")
        }
    }


}