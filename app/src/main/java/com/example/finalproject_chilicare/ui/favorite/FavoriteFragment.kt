package com.example.finalproject_chilicare.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.home.HomeActivity

class FavoriteFragment : Fragment() {

    private lateinit var buttonbackfav : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonbackfav = view.findViewById(R.id.ivBack)
        //button back ke home
        buttonbackfav.setOnClickListener { Intent(activity, HomeActivity::class.java).also {
            startActivity(it)
        } }
    }
}