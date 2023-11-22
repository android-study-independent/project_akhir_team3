package com.example.finalproject_chilicare.ui.home.article.tab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R

class TabAdapter(private val dataList: ArrayList<TabClass>) : RecyclerView.Adapter<TabAdapter.TabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tab_article, parent, false)
        return TabViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val currentItem = dataList[position]

        // Cek apakah judulnya "Filter" atau tidak
        if (currentItem.dataTitle == "Filter") {
            // Jika judulnya "Filter", tampilkan ikon filter
            holder.rvImage.setImageResource(currentItem.dataImage)
            // Tampilkan teks
            holder.rvTitle.text = currentItem.dataTitle
        } else {
            // Jika judulnya bukan "Filter", sembunyikan ikon filter
            holder.rvImage.visibility = View.GONE
            // Tampilkan teks
            holder.rvTitle.text = currentItem.dataTitle
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvImage: ImageView = itemView.findViewById(R.id.image)
        val rvTitle: TextView = itemView.findViewById(R.id.title)
    }
}
