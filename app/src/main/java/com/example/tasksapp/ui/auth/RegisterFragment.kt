package com.example.tasksapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentRegisterBinding
import com.example.tasksapp.ui.BaseFragment
import com.example.tasksapp.util.FirebaseHelper
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : BaseFragment()  {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

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
        // Initialize Firebase Auth
        auth = Firebase.auth
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
                hideKeyboard()
                binding.pbRf.isVisible = true

                registerUser(email, password)
            }else {
                showBottomSheet(message = getString(R.string.password_empty))
            }
        }else {
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.pbRf .isVisible = false

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