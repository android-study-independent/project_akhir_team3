package com.example.finalproject_chilicare.adapter.lms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.lms.DataModulResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS

class CardLmsMateriAdapter( private var listMateriLms : List<ListMateriLMS>) : RecyclerView.Adapter<CardLmsMateriAdapter.CardMateriHolder>() {


    var cardClick : ((ListMateriLMS) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMateriHolder {
       val view
        = LayoutInflater.from(parent.context).inflate(R.layout.card_lms_materi,parent,false)
        return CardMateriHolder(view)
    }

    override fun getItemCount(): Int {
        return  listMateriLms.size
    }

    fun updateData(newData : List<ListMateriLMS>) {
        listMateriLms = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CardMateriHolder, position: Int) {
//        holder.bindItemView(listMateriLms[position])
        val result = listMateriLms[position]
        holder.titlemateri.text = result.judulMateri
        holder.descmateri.text = result.shortDesc

        holder.itemView.setOnClickListener {
            cardClick?.invoke(listMateriLms[position])
        }

    }

    inner class CardMateriHolder(private val view: View) : RecyclerView.ViewHolder(view){

        val titlemateri = view.findViewById<TextView>(R.id.tv_MateriLms)
        val descmateri = view.findViewById<TextView>(R.id.tv_DecMateri)
        init {
            view.setOnClickListener {
                cardClick?.invoke(listMateriLms[adapterPosition])

            }
        }

        fun bindItemView(cardmateri:ListMateriLMS ) {
            val titlemateri = view.findViewById<TextView>(R.id.tv_MateriLms)
            val descmateri = view.findViewById<TextView>(R.id.tv_DecMateri)
//            val covermateri = itemView.findViewById<ImageView>(R.id.iv_GambarMateriLms)
//            val checkbox = itemView.findViewById<CheckBox>(R.id.checkblms)

            titlemateri.text= cardmateri.judulMateri
            descmateri.text = cardmateri.shortDesc

        }


    }
}