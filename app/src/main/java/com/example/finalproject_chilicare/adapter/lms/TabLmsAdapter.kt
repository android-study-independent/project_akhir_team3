package com.example.finalproject_chilicare.adapter.lms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.lms.ModulMateri
import com.example.finalproject_chilicare.data.response.lms.TabLmsResponse
import com.example.finalproject_chilicare.utils.OnTabClickListener

class TabLmsAdapter(private  val statuslist:ArrayList<TabLmsResponse>, private val  listener : OnTabClickListener): RecyclerView.Adapter<TabLmsAdapter.TabViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tab_lms,parent,false)
        return TabViewHolder(itemView,listener)
    }

    override fun getItemCount(): Int {
        return statuslist.size
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
       val currentItem = statuslist[position]
        holder.rvStatus.text = currentItem.status
    }


     class TabViewHolder(itemView: View, private val listener: OnTabClickListener) : RecyclerView.ViewHolder(itemView) {
         val rvStatus: TextView = itemView.findViewById(R.id.tvstatus)
        init {
            itemView.setOnClickListener {
                listener.onTabClick(rvStatus.text.toString())

            }
        }
    }
}