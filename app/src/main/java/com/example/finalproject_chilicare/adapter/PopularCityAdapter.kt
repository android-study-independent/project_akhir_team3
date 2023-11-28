package com.example.finalproject_chilicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.models.CurrentWeatherDetails

class PopularCityAdapter (private val listCity : List<CurrentWeatherDetails>) : RecyclerView.Adapter<PopularCityAdapter.PopularCityHolder> (){

    var onItemClick : ((CurrentWeatherDetails) -> Unit)? = null

    inner class PopularCityHolder (private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView (popularCity : CurrentWeatherDetails) {

            val nameCity = view.findViewById<TextView>(R.id.textCity)

            nameCity.text = popularCity.city

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCityHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_popular_city, parent, false)
        return PopularCityHolder(view)
    }

    override fun getItemCount(): Int {
        return listCity.size
    }

    override fun onBindViewHolder(holder: PopularCityHolder, position: Int) {

        val itemCity = listCity[position]
        holder.bindView(listCity[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemCity)
        }


    }
}