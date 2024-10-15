package com.example.tasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentHomeBinding
import com.example.tasksapp.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
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