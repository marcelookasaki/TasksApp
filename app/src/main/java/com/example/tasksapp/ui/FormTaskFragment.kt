package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentFormTaskBinding
import com.example.tasksapp.databinding.FragmentHomeBinding
import com.example.tasksapp.util.initToolBar


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar(binding.apMtbFtf)
        initListeners()
    }

    private fun initListeners() {
        binding.btnSalvar.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val description = binding.etFormTask.text.toString().trim()

        if (description.isNotEmpty()) {
            Toast.makeText(requireContext(), "OK!", Toast.LENGTH_LONG).show()
        }else {
            Toast.makeText(requireContext(), "Digite uma nova tarefa!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}