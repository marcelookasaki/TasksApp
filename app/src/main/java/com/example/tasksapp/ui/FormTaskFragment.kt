package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
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

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var status: Status = Status.TODO
    private var newTask: Boolean = true

    private lateinit var reference: DatabaseReference

    private lateinit var auth: FirebaseAuth

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


        initListeners()
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

    private fun validateData() {
        val description = binding.etFormTask.text.toString().trim()

        if (description.isNotEmpty()) {

            binding.pbFtf.isVisible = true

            if (newTask) task = Task()
            task.id = reference.database.reference.push().key?:""
            task.description = description
            task.status = status

            saveTask()

        }else {
            showBottomSheet(message = getString(R.string.description_empty_form_task))
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