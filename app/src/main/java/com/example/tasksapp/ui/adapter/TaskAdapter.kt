package com.myo.tasksapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.ItemTaskBinding
import com.myo.tasksapp.data.model.Task

class TaskAdapter(
    private val context: Context,
    private val taskList: List<Task>
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]

        holder.binding.tvDescription.text = task.description

        setIndicator(task, holder)
    }

    private fun setIndicator(task: Task, holder: MyViewHolder) {

        when(task.status) {
            Status.TODO -> {
                holder.binding.ibPrevious.isVisible = false
            }
            Status.DOING -> {
                holder.binding.ibPrevious.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_todo)
                )
                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_done)
                )
            }
            Status.DONE -> {
                holder.binding.ibNext.isVisible = false
            }
        }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root)
}