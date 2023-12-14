package com.example.finalproject_chilicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.PopularCityResponse

class PopularCityAdapter(private val dataList: ArrayList<PopularCityResponse>, private  val itemClickCity : OnItemClickListener) : RecyclerView.Adapter<PopularCityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_popular_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.rvTitle.text = currentItem.dataTitle
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

 inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.titleCity)

        init {
            itemView.setOnClickListener {
                itemClickCity.onItemClick(adapterPosition.toString())
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(city: String)
    }

}