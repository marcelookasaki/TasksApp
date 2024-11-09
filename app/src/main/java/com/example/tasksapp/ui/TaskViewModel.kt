package com.example.tasksapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myo.tasksapp.data.model.Task

class TaskViewModel : ViewModel() {

    private val _taskList = MutableLiveData<StateView<List<Task>>>()
    val taskList: LiveData<StateView<List<Task>>> = _taskList

    private val _taskInsert = MutableLiveData<StateView<Task>>()
    val taskInsert: LiveData<StateView<Task>> = _taskInsert

    private val _taskUpdate = MutableLiveData<StateView<Task>>()
    val taskUpdate: LiveData<StateView<Task>> = _taskUpdate

    private val _taskDelete = MutableLiveData<StateView<Task>>()
    val taskDelete: LiveData<StateView<Task>> = _taskDelete

    fun getTasks() {
        try {

        } catch (ex: Exception) {
            _taskList.postValue(StateView.OnError(ex.message.toString()))
        }
    }


    fun insertTask(task: Task) {
        try {

        }catch (ex: Exception) {
            _taskInsert.postValue(StateView.OnError(ex.message.toString()))
        }
    }

    fun updateTask(task: Task) {
        try {

        }catch (ex: Exception) {
            _taskUpdate.postValue(StateView.OnError(ex.message.toString()))
        }
    }

    fun deleteTask(task: Task) {
        try {

        }catch (ex: Exception) {
            _taskDelete.postValue(StateView.OnError(ex.message.toString()))
        }
    }
}