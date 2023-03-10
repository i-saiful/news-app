package com.saiful.newsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saiful.newsapp.model.Tab
import com.saiful.newsapp.ui.fragment.tab.*

class TabAdapter(manager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(manager, lifecycle) {
    companion object {
        val tabList = listOf(
            Tab(BusinessFragment(), "Business"),
            Tab(EntertainmentFragment(), "Entertainment"),
            Tab(GeneralFragment(), "General"),
            Tab(HealthFragment(), "Health"),
            Tab(ScienceFragment(), "Science"),
            Tab(SportsFragment(), "Sports"),
            Tab(TechnologyFragment(), "Technology")
        )
    }

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position].fragment
    }
}