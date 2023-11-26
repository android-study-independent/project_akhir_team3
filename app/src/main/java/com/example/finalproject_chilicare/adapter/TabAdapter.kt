package com.example.finalproject_chilicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.TabResponse

class TabAdapter(private val dataList: ArrayList<TabResponse>) : RecyclerView.Adapter<TabAdapter.TabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tab_article, parent, false)
        return TabViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.rvTitle.text = currentItem.dataTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)
    }
}