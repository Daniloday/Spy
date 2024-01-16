package com.missclick.spy.ui.viewpager

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import com.missclick.spy.R
import com.missclick.spy.adapters.ViewPagerAdapter
import com.missclick.spy.databinding.FragmentGuideBinding
import com.missclick.spy.databinding.FragmentSplashBinding
import com.missclick.spy.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment(R.layout.fragment_view_pager){

    private val binding by viewBinding(FragmentViewPagerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_menuFragment)
        }
//        binding.tabDots.setupWithViewPager(binding.viewPager)
        TabLayoutMediator( binding.tabDots, binding.viewPager) { tab, position ->
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 6) binding.btnNext.text = getString(R.string.next)
                else binding.btnNext.text = getString(R.string.skip)
            }
        })

    }
}



