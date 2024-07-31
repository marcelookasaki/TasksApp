package com.myo.tasksapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.myo.tasksapp.R
import com.myo.tasksapp.databinding.BottomSheetBinding

fun Fragment.initToolBar(toolbar: Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val bottomSheetBinding: BottomSheetBinding = BottomSheetBinding.inflate(
        layoutInflater, null, false)

    bottomSheetBinding.tvTitle.text = getText(titleDialog ?: R.string.text_title_warning)
    bottomSheetBinding.tvMessage.text = getText(message)
    bottomSheetBinding.btnOK.text = getText(titleButton ?: R.string.text_button_warning)
    bottomSheetBinding.btnOK.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }
    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()
}