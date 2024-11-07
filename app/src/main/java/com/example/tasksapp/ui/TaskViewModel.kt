package com.example.tasksapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasksapp.util.FirebaseHelper
import com.myo.tasksapp.data.model.Task

class TaskViewModel : ViewModel() {

    private val _taskInsert = MutableLiveData<Task>()
    val taskInsert: LiveData<Task> = _taskInsert

    private val _taskUpdate = MutableLiveData<Task>()
    val taskUpdate: LiveData<Task> = _taskUpdate

    fun insertTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserID())
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->

                if (result.isSuccessful) {
                    _taskInsert.postValue(task)
                }
            }
    }

    fun setUpdateTask(task: Task) {
        _taskUpdate.value = task
    }
}