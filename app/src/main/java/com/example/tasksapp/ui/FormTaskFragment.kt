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
import androidx.navigation.fragment.navArgs
import com.example.tasksapp.R
import com.example.tasksapp.data.model.Status
import com.example.tasksapp.databinding.FragmentFormTaskBinding
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.myo.tasksapp.data.model.Task


class FormTaskFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentFormTaskBinding? = null
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var task: Task
    private lateinit var reference: DatabaseReference
    private lateinit var auth: FirebaseAuth

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
        reference = Firebase.database.reference

        // Initialize Firebase Auth
        auth = com.google.firebase.Firebase.auth

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
            if (newTask) {
                task = Task()
                task.id = reference.database.reference.push().key?:""
            }
            task.description = description
            task.status = status
            saveTask()
        }else {
            showBottomSheet(message = getString(R.string.description_empty_form_task_ftf))
        }
    }

    private fun saveTask() {
        reference
            .child("tasks")
            .child(auth.currentUser?.uid?:"")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->

                if (result.isSuccessful){
                    Toast.makeText(
                        requireContext(),
                        R.string.task_saved_success,
                        Toast.LENGTH_LONG
                    ).show()

                    if (newTask) {
                        findNavController().popBackStack()
                    }else {
                        viewModel.setUpdateTask(task)
                        binding.pbFtf.isVisible = false
                    }
                }else {
                    binding.pbFtf.isVisible = false
                    showBottomSheet(message = getString(R.string.generic_error))
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}