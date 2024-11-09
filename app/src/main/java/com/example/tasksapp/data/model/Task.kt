package com.myo.tasksapp.data.model

import android.os.Parcelable
import com.example.tasksapp.data.model.Status
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var status: Status = Status.TODO
) : Parcelable