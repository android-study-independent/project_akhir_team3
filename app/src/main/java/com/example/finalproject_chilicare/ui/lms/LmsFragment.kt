package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsModulAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.dataclass.ListModulArtikel
import com.example.finalproject_chilicare.ui.home.HomeActivity
import kotlinx.coroutines.launch

class LmsFragment : Fragment() {


    private lateinit var rvcardModul : RecyclerView
    private lateinit var cardlmsadapter : CardLmsModulAdapter
    lateinit var btnback : ImageView
//    private lateinit var date : TextView
//    private lateinit var title : TextView
//    private lateinit var desc : TextView
//    private lateinit var cover : ImageView
    private var cardlistlms = mutableListOf<CardLmsResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnback = view.findViewById(R.id.ivBacklms)

        //button back home
        btnback.setOnClickListener { Intent(activity,HomeActivity::class.java).also {
            startActivity(it)
        } }

        //rv card lms
        rvcardModul = view.findViewById(R.id.rv_cardLms)
        rvcardModul.layoutManager = LinearLayoutManager(requireContext())
        cardlmsadapter = CardLmsModulAdapter(cardlistlms)
        rvcardModul.adapter = cardlmsadapter


        //Get data API LIFECYCLE
        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllLms()
            result.data
                .map {
                    Log.d("Lms","hasi GET API -> ${it}")
                    cardlistlms.add(it)
                }
            //update data recylerview
            cardlmsadapter.notifyDataSetChanged()
        }

        // Uuntuk pindah halaman detail LMS
        cardlmsadapter.clicklmsModul ={
            Log.d("lms","klik hasil ${it}")
            val intent = Intent(activity, DetailLMSActivity::class.java)
            startActivity(intent)
        }
    }



}