package com.example.finalproject_chilicare.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.ViewPagerAdapter
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class SplashFragment : Fragment() {
    lateinit var viewPage: ViewPager2
    lateinit var adapter: ViewPagerAdapter
    lateinit var indikator: IndicatorView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Handler(Looper.getMainLooper()).postDelayed({

            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginActivity)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }

        }, 3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)

        viewPage.findViewById<ViewPager2>(R.id.viewPagerOnboarding)

        indikator.findViewById<IndicatorView>(R.id.indicator_view)

        viewPage.adapter = adapter

        indikator.apply {
            setSliderWidth(resources.getDimension(R.dimen.space_10))
            setSliderHeight(resources.getDimension(R.dimen.space_5))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(viewPage)
        }


    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}