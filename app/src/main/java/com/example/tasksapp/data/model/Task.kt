package com.myo.tasksapp.data.model

import android.os.Parcelable
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.util.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String = "",
    var description: String = "",
    var status: Status = Status.TODO
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
