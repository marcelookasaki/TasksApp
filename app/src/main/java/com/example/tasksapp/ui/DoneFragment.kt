package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.FragmentDoneBinding
import com.myo.tasksapp.data.model.Task
import com.myo.tasksapp.ui.adapter.TaskAdapter


class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(getTasks())
    }
    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)
        binding.rvTasksDone.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasksDone.setHasFixedSize(true)
        binding.rvTasksDone.adapter = taskAdapter
    }
    private fun getTasks() = listOf<Task>(
        Task("0","Estudo o livro dos espíritos", Status.DONE),
        Task("1","Pedir whey, tmz e creatina", Status.DONE),
        Task("2","Ajustando as atividades", Status.DONE),
        Task("3","Planejamento ajuste do cardápio", Status.DONE),
        Task("4","Ajustando os planejamentos", Status.DONE),
        Task("5","Estudando espiritismo", Status.DONE)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}