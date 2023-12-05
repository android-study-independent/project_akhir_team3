package com.example.finalproject_chilicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.dataclass.ListModulArtikel

class CardLmsModulAdapter(private val lmslist:List<ListModulArtikel>) : RecyclerView.Adapter<CardLmsModulAdapter.LmsViewHolder>() {

    class LmsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val date = view.findViewById<TextView>(R.id.tv_Tanggal)
        val title = view.findViewById<TextView>(R.id.tv_JudulBesar)
        val desc = view.findViewById<TextView>(R.id.tv_DescSingkat)
        val cover = view.findViewById<ImageView>(R.id.iv_GambarLMS)
//        val progress = view.findViewById<ProgressBar>(R.id.pb_lms)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LmsViewHolder {
       val view : View = LayoutInflater.from(parent.context)
           .inflate(R.layout.card_lms_module,parent,false)
        return LmsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lmslist.size
    }

    override fun onBindViewHolder(holder: LmsViewHolder, position: Int) {
        val lms = lmslist[position]
        holder.date.text = lms.date
        holder.title.text = lms.title
        holder.desc.text = lms.desc
        holder.cover.setImageResource(lms.cover)
//        holder.progress.progress = lms.progress
    }
}