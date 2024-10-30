package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentHomeBinding
import com.example.tasksapp.ui.adapter.ViewPagerAdapter
import com.example.tasksapp.util.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        initListeners()
        initTabs()
    }

    private fun initListeners() {
        binding.ibExit.setOnClickListener {

            showBottomSheet(
                titleButton = R.string.text_btn_dialog_confirm,
                titleDialog = R.string.text_title_dialog_confirm_logout,
                message = getString(R.string.text_message_dialog_confirm_logout),
                onClick = {
                    auth.signOut()
                    findNavController().navigate(R.id.action_homeFragment_to_authentication)
                }
            )
        }
    }

    private fun initTabs() {
        val vpAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager2.adapter = vpAdapter

        vpAdapter.addFragment(TodoFragment(), R.string.status_task_todo )
        vpAdapter.addFragment(DoingFragment(), R.string.status_task_doing )
        vpAdapter.addFragment(DoneFragment(), R.string.status_task_done )

        binding.viewPager2.offscreenPageLimit = vpAdapter.itemCount

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = getString(vpAdapter.getTitle(position))
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}