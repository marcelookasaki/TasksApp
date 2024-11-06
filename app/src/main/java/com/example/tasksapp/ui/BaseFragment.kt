package com.example.tasksapp.ui

import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager

open class BaseFragment : Fragment() {
    fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm = activity?.getSystemService(
                android.content.Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}