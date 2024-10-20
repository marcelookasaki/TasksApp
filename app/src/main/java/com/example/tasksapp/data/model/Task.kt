package com.myo.tasksapp.data.model

import android.os.Parcelable
import com.example.tasksapp.data.model.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: String,
    val description: String,
    val status: Status = Status.TODO
) : Parcelable
