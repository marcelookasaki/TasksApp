package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.FragmentDoingBinding
import com.myo.tasksapp.data.model.Task
import com.myo.tasksapp.ui.adapter.TaskAdapter


class DoingFragment : Fragment() {

    private var _binding: FragmentDoingBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(getTasks())
    }
    private fun initRecyclerView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList)
        binding.rvTasksDoing.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasksDoing.setHasFixedSize(true)
        binding.rvTasksDoing.adapter = taskAdapter
    }
    private fun getTasks() = listOf<Task>(
        Task("0","Fazendo o curso kotlin", Status.DOING),
        Task("1","Atualizando os complentos", Status.DOING),
        Task("2","Ajustando as atividades", Status.DOING),
        Task("3","Planejamento ajuste do cardápio", Status.DOING),
        Task("4","Ajustando os planejamentos", Status.DOING),
        Task("5","Estudando espiritismo", Status.DOING)
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}