package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsModulAdapter
import com.example.finalproject_chilicare.adapter.lms.TabLmsAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.TabLmsResponse
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.utils.OnTabClickListener
import kotlinx.coroutines.launch

class LmsFragment : Fragment(), OnTabClickListener {


    private lateinit var rvcardModul: RecyclerView
    private lateinit var rvTabLms: RecyclerView
    private lateinit var cardlmsadapter: CardLmsModulAdapter
    lateinit var btnback: ImageView
    lateinit var searchModul : EditText
    private var cardlistlms = mutableListOf<CardLmsResponse>()
    private lateinit var tabResponse: ArrayList<TabLmsResponse>
    private lateinit var progressBar: ProgressBar


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
        searchModul = view.findViewById(R.id.etCariMateriLms)

        progressBar = view.findViewById(R.id.progressBarLms)

        //button back home
        btnback.setOnClickListener {
            Intent(activity, HomeActivity::class.java).also {
                startActivity(it)
            }
        }

        //Search Bar Materi LMS
        searchModul.addTextChangedListener { text ->
            val query = text.toString().trim()

            if (query.isEmpty()) {
                cardlmsadapter.updateData(cardlistlms)
            } else {
                cardlmsadapter.searchModul(query)
            }
        }


        //Tainisiasi tab LMS adapter
        rvTabLms = view.findViewById(R.id.rv_tabLms)
        rvTabLms.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTabLms.setHasFixedSize(true)
        tabResponse = ArrayList()
        rvTabLms.adapter = TabLmsAdapter(tabResponse, this)

        //rv card lms adapter
        rvcardModul = view.findViewById(R.id.rv_cardLmsModul)
        cardlmsadapter = CardLmsModulAdapter(cardlistlms)


        //Get data API LIFECYCLE
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllLms(
                )
            result.data
                .map {
                    Log.d("Lms", "hasil GET API -> ${it}")
                    cardlistlms.add(it)
                    // RV Modul LMS
                    rvcardModul.adapter = cardlmsadapter
                    rvcardModul.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                    progressBar.visibility = View.GONE
                }

            // RV TAB LMS
            tabResponse.addAll(cardlistlms
                .filter { it.status != null}
                .distinctBy { it.status }
                .map { TabLmsResponse(it.status!!) })

            //Update RecyclerView
            cardlmsadapter.notifyDataSetChanged()
            rvTabLms.adapter?.notifyDataSetChanged()
        }

        // Uuntuk pindah halaman materi LMS
        cardlmsadapter.clicklmsModul = {
            Log.d("lms", "klik hasil ${it}")
            val intent = Intent(activity, MateriLMSActivity::class.java)
//            intent.putParcelableArrayListExtra("modulLms",ArrayList(cardlistlms))
            intent.putExtra("id", it.id)
            val id = it.id
            Log.d("lms_id", "onViewCreated: $id ")

            startActivity(intent)
        }
    }

    override fun onTabClick(status: String) {
        val filterStatus = cardlistlms.filter { it.status == status }

        cardlmsadapter.updateData(filterStatus)
    }


}