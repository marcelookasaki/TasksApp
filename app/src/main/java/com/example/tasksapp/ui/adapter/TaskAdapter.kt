package com.myo.tasksapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.ItemTaskBinding
import com.myo.tasksapp.data.model.Task

class TaskAdapter(
    private val context: Context,
    private val taskSelected: (Task, Int) -> Unit
) : ListAdapter<Task, TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        val SELECT_PREVIOUS: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_DETAILS: Int = 4
        val SELECT_NEXT: Int = 5

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.description == newItem.description
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem == newItem &&
                        oldItem.description == newItem.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = getItem(position)

        holder.binding.tvDescription.text = task.description

        setIndicator(task, holder)

        holder.binding.btnDelete.setOnClickListener { taskSelected(task, SELECT_REMOVE) }
        holder.binding.btnEdit.setOnClickListener { taskSelected(task, SELECT_EDIT) }
        holder.binding.btnDetails.setOnClickListener { taskSelected(task, SELECT_DETAILS) }
    }

    private fun setIndicator(task: Task, holder: MyViewHolder) {
        when(task.status) {
            Status.TODO -> {
                holder.binding.ibPrevious.isVisible = false
                holder.binding.ibNext.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            Status.DOING -> {
                holder.binding.ibPrevious.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_todo)
                )
                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.status_done)
                )
                holder.binding.ibPrevious.setOnClickListener { taskSelected(task, SELECT_PREVIOUS) }
                holder.binding.ibNext.setOnClickListener { taskSelected(task, SELECT_NEXT) }
            }
            Status.DONE -> {
                holder.binding.ibNext.isVisible = false
                holder.binding.ibPrevious.setOnClickListener { taskSelected(task, SELECT_PREVIOUS) }

            }
        }
    }

    inner class MyViewHolder(val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root)
}