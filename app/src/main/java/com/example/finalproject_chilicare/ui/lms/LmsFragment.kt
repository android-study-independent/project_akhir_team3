package com.example.finalproject_chilicare.ui.lms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardLmsModulAdapter
import com.example.finalproject_chilicare.dataclass.ListModulArtikel

class LmsFragment : Fragment() {
    private val listlms = ArrayList<ListModulArtikel>()

    private lateinit var rvcardModul : RecyclerView
    private lateinit var lmsadapter : CardLmsModulAdapter
    private lateinit var date : TextView
    private lateinit var title : TextView
    private lateinit var desc : TextView
    private lateinit var cover : ImageView


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

        //rv card lms
        rvcardModul = view.findViewById(R.id.rv_cardLms)
        rvcardModul.setHasFixedSize(true)
        lmsadapter = CardLmsModulAdapter(listlms)
        rvcardModul.adapter = lmsadapter
        listlms.addAll(getListLms())
        rvcardModul.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getListLms(): ArrayList<ListModulArtikel>{
        val date = resources.getStringArray(R.array.datelms)
        val title = resources.getStringArray(R.array.titlelms)
        val desc = resources.getStringArray(R.array.desclms)
        val cover = resources.obtainTypedArray(R.array.coverlms)
        val progress = resources.getInteger(R.integer.progress)
        val listlms = ArrayList<ListModulArtikel>()
        for (i in date.indices) {
            val lms = ListModulArtikel(
                date[i],title[i],desc[i],cover.getResourceId(i,-1),
            )
            listlms.add(lms)
        }
        return listlms
    }



}