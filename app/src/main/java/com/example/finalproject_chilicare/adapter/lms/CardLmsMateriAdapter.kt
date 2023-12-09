package com.example.finalproject_chilicare.adapter.lms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS

class CardLmsMateriAdapter( private var listMateriLms : List<ListMateriLMS>) : RecyclerView.Adapter<CardLmsMateriAdapter.CardMateriHolder>() {

    var cardClick : ((ListMateriLMS) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardMateriHolder {
       val listiew
        = LayoutInflater.from(parent.context).inflate(R.layout.card_lms_materi,parent,false)
        return CardMateriHolder(listiew)
    }

    override fun getItemCount(): Int {
        return  listMateriLms.size
    }

    override fun onBindViewHolder(holder: CardMateriHolder, position: Int) {
        holder.bindItemView(listMateriLms[position])

        holder.itemView.setOnClickListener {
            cardClick?.invoke(listMateriLms[position])
        }

    }

    fun updateData (datNew : List<ListMateriLMS>) {
        listMateriLms = datNew
        notifyDataSetChanged()
    }

    inner class CardMateriHolder(private  val view : View) : RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                cardClick?.invoke(listMateriLms[adapterPosition])

            }
        }

//        fun bindView(materiLms : CardLmsResponse) {
//            // inisiasi view
//            val title = view.findViewById<TextView>(R.id.tvJudulMateri)
//            val image = view.findViewById<ImageView>(R.id.ivImgmainlms)
//            val descjudul = view.findViewById<TextView>(R.id.tvmateriLms)
//            val watchtime = view.findViewById<TextView>(R.id.tvWatchTime)
//            val totalmateri = view.findViewById<TextView>(R.id.tvtotalmateri)
//            val date = view.findViewById<TextView>(R.id.rl_tanggalLms)
//
//            title.text = materiLms.judul
//            image.setImageResource(materiLms.cover)
//            descjudul.text = materiLms.desc
//            watchtime.text = materiLms.learningTime
//            totalmateri.text=materiLms.totalMateri.toString()
//            date.text = materiLms.tanggal
//
//        }

        fun bindItemView(cardmateri:ListMateriLMS ) {
            val titlemateri = itemView.findViewById<TextView>(R.id.tv_MateriLms)
            val descmateri = itemView.findViewById<TextView>(R.id.tv_DecMateri)
            val covermateri = itemView.findViewById<ImageView>(R.id.iv_GambarMateriLms)

            titlemateri.text= cardmateri.judulMateri
            descmateri.text = cardmateri.shortDesc
            covermateri.setImageResource(cardmateri.image)

        }


    }
}