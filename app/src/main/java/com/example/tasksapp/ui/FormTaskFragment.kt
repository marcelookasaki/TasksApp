package com.example.tasksapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasksapp.R
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.FragmentFormTaskBinding
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet
import com.myo.tasksapp.data.model.Task


class FormTaskFragment : BaseFragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentFormTaskBinding? = null
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var task: Task

    private val args: FormTaskFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar(binding.apMtbFtf)
        getArgs()
        initListeners()
    }

    private fun getArgs() {
        args.task.let {
            if (it != null) {
                this.task = it
                configTask()
            }
        }
    }

    private fun initListeners() {
        binding.btnSalvar.setOnClickListener {
            observeViewModel()
            validateData()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            status = when(id) {
                R.id.rb_todo -> Status.TODO
                R.id.rb_doing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun observeViewModel() {
        viewModel.taskInsert.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                R.string.task_saved_success,
                Toast.LENGTH_LONG
            ).show()

            findNavController().popBackStack()
        }

        viewModel.taskUpdate.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                R.string.task_updated_success,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun configTask() {
        newTask = false
        status = task.status
        binding.tvTitleMtbFtf.setText(R.string.toolBar_updating_ftf)
        binding.etFormTask.setText(task.description)
        setStatus()
    }

    private fun setStatus() {
        @Suppress("UNUSED_VARIABLE") val id =
            binding.radioGroup.check(
                when (task.status) {
                    Status.TODO -> R.id.rb_todo
                    Status.DOING -> R.id.rb_doing
                    else -> R.id.rb_done
                }
            )
    }

    private fun validateData() {
        val description = binding.etFormTask.text.toString().trim()

        if (description.isNotEmpty()) {

            binding.pbFtf.isVisible = true

            if (newTask) task = Task()

            task.description = description
            task.status = status

            if (newTask) {
                viewModel.insertTask(task)
            }else {
                viewModel.updateTask(task)
            }

        }else {
            showBottomSheet(message = getString(R.string.description_empty_form_task_ftf))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}