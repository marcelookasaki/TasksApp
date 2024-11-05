package com.example.tasksapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentLoginBinding
import com.example.tasksapp.util.FirebaseHelper
import com.example.tasksapp.util.showBottomSheet


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {

        binding.btnLoginLf.setOnClickListener {
            validateData()
        }

        binding.tvRegisterLf.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvRecoverAccountLf.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
    }

    private fun validateData() {
        val email = binding.etEmailLf.text.toString().trim()
        val password = binding.etPasswordLf.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                binding.pbLf.isVisible = true

                userLogin(email, password)
            }else {
                showBottomSheet(message = getString(R.string.password_empty))
            }
        }else {
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    private fun userLogin(email: String, password: String) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.pbLf.isVisible = false
                    showBottomSheet(
                        message = getString(
                            FirebaseHelper.validError(
                            task.exception?.message.toString()))
                    )
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}