package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsModulAdapter
import com.example.finalproject_chilicare.adapter.lms.TabLmsAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.article.TabResponse
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.ModulMateri
import com.example.finalproject_chilicare.data.response.lms.ModulStatusRespn
import com.example.finalproject_chilicare.dataclass.ListModulArtikel
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.utils.OnTabClickListener
import kotlinx.coroutines.launch

class LmsFragment : Fragment(), OnTabClickListener {


    private lateinit var rvcardModul: RecyclerView
    private lateinit var rvTabLms: RecyclerView
    private lateinit var cardlmsadapter: CardLmsModulAdapter
    lateinit var btnback: ImageView
    private var cardlistlms = mutableListOf<ModulMateri>()
    private lateinit var tabResponse: ArrayList<ModulMateri>


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

        // inisiasi  xml
        btnback = view.findViewById(R.id.ivBacklms)

        //button back home
        btnback.setOnClickListener {
            Intent(activity, HomeActivity::class.java).also {
                startActivity(it)
            }
        }


        //Tab LMS
        rvTabLms = view.findViewById(R.id.rv_tabLms)
        rvTabLms.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTabLms.setHasFixedSize(true)
        tabResponse = ArrayList()
        rvTabLms.adapter = TabLmsAdapter(tabResponse, this)

        //rv card lms
        rvcardModul = view.findViewById(R.id.rv_cardLms)
        rvcardModul.layoutManager = LinearLayoutManager(requireContext())
        cardlmsadapter = CardLmsModulAdapter(cardlistlms)
        rvcardModul.adapter = cardlmsadapter


        //Get data API LIFECYCLE
        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllModul(
                    status = "proses"
                )
            result.data
                .map {
                    Log.d("Lms", "hasil GET API -> ${it}")
                    cardlistlms.add(it)
                }

            tabResponse.addAll(cardlistlms
                .distinctBy { it.status }
                .filter { it.status != null })
            //update data recylerview
            cardlmsadapter.notifyDataSetChanged()
        }

        // Uuntuk pindah halaman detail LMS
        cardlmsadapter.clicklmsModul = {
            Log.d("lms", "klik hasil ${it}")
            val intent = Intent(activity, DetailLMSActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTabClick(status: String) {
        val filterStatus = cardlistlms.filter { it.status == status }

        cardlmsadapter.updateData(filterStatus)
    }


}