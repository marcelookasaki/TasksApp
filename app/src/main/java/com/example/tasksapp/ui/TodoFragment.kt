package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.R
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.FragmentTodoBinding
import com.example.tasksapp.util.FirebaseHelper
import com.example.tasksapp.util.showBottomSheet
import com.myo.tasksapp.data.model.Task
import com.myo.tasksapp.ui.adapter.TaskAdapter
import com.myo.tasksapp.ui.adapter.TaskTopAdapter


@Suppress("UnusedEquals", "UnusedEquals", "UnusedEquals", "UnusedEquals", "UnusedEquals",
    "UnusedEquals"
)
class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskTopAdapter: TaskTopAdapter

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initRecyclerView()
        observerViewModel()
        viewModel.getTasks(Status.TODO)
    }

    private fun initListeners() {
        binding.fabTf.setOnClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToFormTaskFragment(null)
            findNavController().navigate(action)
        }
        observerViewModel()
    }

    private fun observerViewModel() {

        viewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            binding.todoFragmentPB.isVisible = false
            listEmpty(taskList)
            taskAdapter.submitList(taskList)
        }

        viewModel.taskInsert.observe(viewLifecycleOwner) { task ->
            if (task.status == Status.TODO) {

                // Armazena a lista atual do adapter
                val oldList = taskAdapter.currentList

                // Gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                val newList = oldList.toMutableList().apply {
                    add(0, task)
                }

                // Envia a lista atualizada para o adapter
                taskAdapter.submitList(newList)

                setPositionRecyclerView()
            }
        }

        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->

            // Armazena a lista atual do adapter
            val oldList = taskAdapter.currentList

            // Gera uma nova lista a partir da lista antiga já com a tarefa atualizada
            val newList = oldList.toMutableList().apply {
                if (updateTask.status == Status.TODO) {
                    find { it.id == updateTask.id }?.description == updateTask.description
                }else {
                    remove(updateTask)
                }
            }

            // Armazena a posição da tarefa a ser atualiza na lista
            val position = newList.indexOfFirst { it.id == updateTask.id }

            // Envia a lista atualizada para o adapter
            taskAdapter.submitList(newList)

            // Atualiza a tarefa pela posição do adapter
            taskAdapter.notifyItemChanged(position)

        }
    }

    private fun setPositionRecyclerView() {
        taskAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {

            }
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {

            }
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {

            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.rvTasksTodo.scrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {

            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {

            }
        })
    }

    private fun initRecyclerView() {
        taskTopAdapter = TaskTopAdapter { task, option ->
            optionSelected(task, option)
        }
        taskAdapter = TaskAdapter(requireContext()) { task, option ->
            optionSelected(task, option)
        }

        val concatAdapter = ConcatAdapter(taskTopAdapter, taskAdapter)

        with(binding.rvTasksTodo) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = concatAdapter
        }
    }

    private fun optionSelected(task: Task, option: Int) {
        when(option) {
            TaskAdapter.SELECT_REMOVE -> {
                showBottomSheet(
                    titleDialog = R.string.title_dialog_delete,
                    message = getString(R.string.message_dialog_delete),
                    titleButton = R.string.text_btn_dialog_confirm,
                    onClick = {
                        deleteTask(task)
                    }
                )
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToFormTaskFragment(task)
                findNavController().navigate(action)
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(
                    requireContext(),
                    "Detalhes de ${task.description}",
                    Toast.LENGTH_LONG)
                    .show()
            }
            TaskAdapter.SELECT_NEXT -> {
                task.status = Status.DOING
                viewModel.updateTask(task)
            }
        }
    }



    private fun deleteTask(task: Task) {
        FirebaseHelper.getDatabase()
            .child("tasks")
            .child(FirebaseHelper.getUserID())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        R.string.task_delete_success,
                        Toast.LENGTH_LONG
                    ).show()

                    // Armazena a lista atual do adapter
                    val oldList = taskAdapter.currentList

                    // Gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                    val newList = oldList.toMutableList().apply {
                        remove(task)
                    }
                    taskAdapter.submitList(newList)
                }else {
                    Toast.makeText(
                        requireContext(),
                        R.string.generic_error,Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun listEmpty(taskList: List<Task>) {
        binding.tvTodoFragmentTaskList.text = if (taskList.isEmpty()) {
            getString(R.string.task_list_empty)
        }else {
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}