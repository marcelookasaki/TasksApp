package com.example.tasksapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentRegisterBinding
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar(binding.abMtbRf)
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegisterRf.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.etEmailRf.text.toString().trim()
        val password = binding.etPasswordRf.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                findNavController().navigate(R.id.action_global_homeFragment)
            }else {
                showBottomSheet(message = R.string.password_empty)
            }
        }else {
            showBottomSheet(message = R.string.email_empty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}