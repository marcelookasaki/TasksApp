package com.example.tasksapp.util

import com.example.tasksapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {
    companion object {
        fun getDatabase() = Firebase.database.reference

        fun getAuth() = FirebaseAuth.getInstance()

        fun getUserID() = getAuth().currentUser?.uid ?: ""

        fun isAuthenticated() = getAuth().currentUser != null

        fun validError(error: String): Int {
            return when {
                error.contains(
                    "There is no user record corresponding to this identifier")
                -> {
                    R.string.account_not_registered_fireAuth
                }
                error.contains(
                    "The email adress is badly formatted")
                -> {R.string.invalid_email_register_fireAuth
                }
                error.contains(
                    "The password is invalid or the user does not have a password")
                -> {R.string.invalid_password_fireAuth
                }
                error.contains(
                    "The email adress is already in use by another account")
                -> {R.string.email_in_use_register_fireAuth
                }
                error.contains(
                    "Password should be at least 6 characters")
                -> {R.string.strong_password_fireAuth
                }
                else -> {R.string.generic_error
                }
            }

        }
    }
}
