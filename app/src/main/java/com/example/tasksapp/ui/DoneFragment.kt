package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        initRecyclerView()
        getTasks()
    }
    private fun initRecyclerView() {
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        with(binding.rvTasksDone) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when(option) {
            TaskAdapter.SELECT_PREVIOUS-> {
                Toast.makeText(
                    requireContext(),
                    "Previous ${task.description}",
                    Toast.LENGTH_LONG)
                    .show()
            }
            TaskAdapter.SELECT_REMOVE -> {
                Toast.makeText(
                    requireContext(),
                    "Removendo ${task.description}",
                    Toast.LENGTH_LONG)
                    .show()
            }
            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(
                    requireContext(),
                    "Editando ${task.description}",
                    Toast.LENGTH_LONG)
                    .show()
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(),
                    "Detalhes de ${task.description}",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun getTasks(){
        val taskList = listOf(
            Task("0","Estudo do livro dos espíritos", Status.DONE),
            Task("1","Pedir whey, zma e creatina", Status.DONE),
            Task("2","Ajustando as atividades", Status.DONE),
            Task("3","Planejamento ajuste do cardápio", Status.DONE),
            Task("4","Ajustando os planejamentos", Status.DONE),
            Task("5","Estudando espiritismo", Status.DONE)
        )
        taskAdapter.submitList(taskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}