package com.example.finalproject_chilicare.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import kotlin.math.min

class BerandaArtikelAdapter(private var listArtikel: List<CardArtikelResponse>) :
    RecyclerView.Adapter<BerandaArtikelAdapter.BerandaArtikelHolder>() {

    private val maxItemCount = 2


    var onItemClick: ((CardArtikelResponse) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BerandaArtikelHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_article, parent, false)
        return BerandaArtikelHolder(view)
    }

    override fun getItemCount(): Int {
        return min(listArtikel.size, maxItemCount)
    }

    fun updateData(newData: List<CardArtikelResponse>) {
        listArtikel = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BerandaArtikelHolder, position: Int) {
        holder.bindView(listArtikel[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listArtikel[position])
        }

    }

    inner class BerandaArtikelHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(berandaArtikel: CardArtikelResponse) {
            //inisiasi view
            val kategori = view.findViewById<TextView>(R.id.tv_title)
            val judul = view.findViewById<TextView>(R.id.tv_subtitle)
            val deskripsi = view.findViewById<TextView>(R.id.tv_description)
            val waktubaca = view.findViewById<TextView>(R.id.tv_KeteranganDibaca)
            val image = view.findViewById<ImageView>(R.id.iv_Gambar)

            kategori.text = berandaArtikel.category
            judul.text = berandaArtikel.title
            deskripsi.text = berandaArtikel.desc
            waktubaca.text = berandaArtikel.readTime

            val path = buildPathCover(berandaArtikel.coverPath)

            Picasso.get()
                .load(path).into(image, object : Callback {
                    override fun onSuccess() {
                        Log.d("homeartikel", "Image loaded successfully")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso", "Error loading image: ${e?.message}")
                    }
                })
        }

    }

    private fun buildPathCover(coverPath: String?): String {
        return coverPath ?: ""

    }
}