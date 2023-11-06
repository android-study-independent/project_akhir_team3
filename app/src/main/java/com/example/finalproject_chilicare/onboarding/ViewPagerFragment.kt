package com.example.finalproject_chilicare.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ViewPagerAdapter
import com.example.finalproject_chilicare.ui.FirstScreen
import com.example.finalproject_chilicare.ui.SecondScreen
import com.example.finalproject_chilicare.ui.ThirdScreen


class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,lifecycle
        )

        view.findViewById<ViewPager2>(R.id.viewPagerOnboarding).adapter=adapter

        return view
    }

}