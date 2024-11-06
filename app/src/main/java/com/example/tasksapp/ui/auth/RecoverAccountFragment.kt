package com.example.tasksapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentRecoverAccountBinding
import com.example.tasksapp.ui.BaseFragment
import com.example.tasksapp.util.FirebaseHelper
import com.example.tasksapp.util.initToolBar
import com.example.tasksapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

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

        // Initialize Firebase Auth
        auth = Firebase.auth

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
            hideKeyboard()
            binding.pbRaf.isVisible = true
            recoverUserAccount(email)
        }else {
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    private fun recoverUserAccount(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showBottomSheet(message = getString(R.string.text_message_recover_account))
                }else{
                    showBottomSheet(
                        message = getString(
                            FirebaseHelper.validError(
                                task.exception?.message.toString()
                            )
                        )
                    )
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}