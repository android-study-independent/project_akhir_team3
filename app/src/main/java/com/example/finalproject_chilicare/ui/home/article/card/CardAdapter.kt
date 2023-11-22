package com.example.finalproject_chilicare.ui.home.article.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import java.util.Locale

class CardAdapter(private var dataList: ArrayList<CardClass>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>(), Filterable {

    private var filteredList: ArrayList<CardClass> = ArrayList()

    init {
        filteredList.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_article, parent, false)
        return CardAdapter.CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = filteredList[position]

        holder.rvTitle.text = currentItem.dataTitle
        holder.rvSubtitle.text = currentItem.dataSubtitle
        holder.rvDescription.text = currentItem.dataDescription
        holder.rvDurasibaca.text = currentItem.dataDurasibaca
        holder.rvImage.setImageResource(currentItem.dataImage)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val rvSubtitle: TextView = itemView.findViewById(R.id.tv_subtitle)
        val rvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val rvDurasibaca: TextView = itemView.findViewById(R.id.tv_KeteranganDibaca)
        val rvImage: ImageView = itemView.findViewById(R.id.iv_Gambar)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = ArrayList<CardClass>()
                if (constraint.isNullOrBlank()) {
                    filteredResults.addAll(dataList)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                    for (item in dataList) {
                        if (item.dataTitle.lowercase(Locale.ROOT).contains(filterPattern)) {
                            filteredResults.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList.clear()
                filteredList.addAll(results?.values as ArrayList<CardClass>)
                notifyDataSetChanged()
            }
        }
    }

    // Fungsi tambahan untuk mengatur filteredList
    fun setFilteredList(list: ArrayList<CardClass>) {
        filteredList.clear()
        filteredList.addAll(list)
        notifyDataSetChanged()
    }
}
