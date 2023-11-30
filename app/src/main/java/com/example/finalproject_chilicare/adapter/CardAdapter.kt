package com.example.finalproject_chilicare.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.CardArtikelResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.Locale

class CardAdapter(private val listArtikel: List<CardArtikelResponse>) : RecyclerView.Adapter<CardAdapter.CardArtikelHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardArtikelHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_article, parent, false)
        return CardArtikelHolder(view)
    }

    override fun onBindViewHolder(holder: CardArtikelHolder, position: Int) {
        holder.bindView(listArtikel[position])
    }

    override fun getItemCount(): Int {
        return listArtikel.size
    }

    inner class CardArtikelHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(artikel: CardArtikelResponse){

            // inisiasi view nya
            val category = view.findViewById<TextView>(R.id.tv_title)
            val title = view.findViewById<TextView>(R.id.tv_subtitle)
            val desc = view.findViewById<TextView>(R.id.tv_description)
            val readTime = view.findViewById<TextView>(R.id.tv_KeteranganDibaca)
            val cover = view.findViewById<ImageView>(R.id.iv_Gambar)

            category.text = artikel.category
            title.text = artikel.title
            desc.text = artikel.desc
            readTime.text = artikel.readTime


            val path = buildCoverPath(artikel.coverPath)

            Picasso.get()
                .load(path)
                .into(cover, object : Callback {
                    override fun onSuccess() {
                        Log.d("Picasso", "Image loaded successfully")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("Picasso", "Error loading image: ${e?.message}")
                    }
                })

        }

        private fun buildCoverPath(coverPath: String?): String {
            return coverPath ?: ""
        }

    }

}