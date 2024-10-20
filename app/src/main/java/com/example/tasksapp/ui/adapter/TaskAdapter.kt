package com.myo.tasksapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.ItemTaskBinding
import com.myo.tasksapp.data.model.Task

class TaskAdapter( private val taskList: List<Task>)
    : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

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
    }

    inner class MyViewHolder(val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root)
}