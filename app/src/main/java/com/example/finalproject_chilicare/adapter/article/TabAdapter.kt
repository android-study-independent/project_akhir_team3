package com.example.finalproject_chilicare.adapter.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.article.TabResponse
import com.example.finalproject_chilicare.utils.OnTabClickListener

class TabAdapter(private val dataList: ArrayList<TabResponse>, private val listener: OnTabClickListener) : RecyclerView.Adapter<TabAdapter.TabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tab_article, parent, false)
        return TabViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.rvTitle.text = currentItem.dataTitle
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TabViewHolder(itemView: View, private val listener: OnTabClickListener) : RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)

        init {
            // Set listener untuk menanggapi klik pada tab
            itemView.setOnClickListener {
                listener.onTabClick(rvTitle.text.toString())
            }
        }
    }
}
