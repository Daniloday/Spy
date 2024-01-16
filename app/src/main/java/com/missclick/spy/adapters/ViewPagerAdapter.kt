package com.missclick.spy.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.missclick.spy.ui.viewpager.GuideFragment

class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        val fragment = GuideFragment()
        fragment.arguments = GuideFragment.newInstance(position)
        return fragment
    }

}