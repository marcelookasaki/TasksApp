package com.example.tasksapp.ui

sealed class StateView<T>(val data: T? = null, val message: String? = null) {

    class OnLoad<T>: StateView<T>()

    class OnSuccess<T>(data: T, message: String? = null): StateView<T>(data, message)

    class OnError<T>(message: String? = null): StateView<T>(null, message)
}