package com.example.tasksapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentRecoverAccountBinding
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar(binding.abMtbRaf)
        initListeners()
    }

    private fun initListeners() {
        binding.btnRecoverAccountRaf.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.etEmailRaf.text.toString().trim()

        if (email.isNotEmpty()) {
            Toast.makeText(requireContext(), "OK!", Toast.LENGTH_LONG).show()
        }else {
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}