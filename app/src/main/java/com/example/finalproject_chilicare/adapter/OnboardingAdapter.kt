package com.example.finalproject_chilicare.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject_chilicare.dataclass.OnboardingData
import com.example.finalproject_chilicare.ui.onboarding.OnboardingFragment

class OnboardingAdapter(fragment : FragmentActivity, val listDatacoba: List<OnboardingData>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return OnboardingFragment.newInstance(
            title = listDatacoba[position].title,
            desc = listDatacoba[position].desc,
            image = listDatacoba[position].image
        )

    }
}