package com.example.finalproject_chilicare.adapter.article

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

class CardAdapter(private var listArtikel: List<CardArtikelResponse>) : RecyclerView.Adapter<CardAdapter.CardArtikelHolder>() {

    // Deklarasikan variabel onItemClick di dalam CardAdapter
    var onItemClick: ((CardArtikelResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardArtikelHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_article, parent, false)
        return CardArtikelHolder(view)
    }

    override fun onBindViewHolder(holder: CardArtikelHolder, position: Int) {
        holder.bindView(listArtikel[position])

        // Set listener untuk menanggapi klik pada item
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(listArtikel[position])
        }
    }

    override fun getItemCount(): Int {
        return listArtikel.size
    }

    fun updateData(newData: List<CardArtikelResponse>) {
        listArtikel = newData
        notifyDataSetChanged()
    }

    fun searchByCategory(query: String) {
        val filteredList = listArtikel.filter { it.category?.contains(query, ignoreCase = true) == true }
        updateData(filteredList)
    }



    inner class CardArtikelHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        init {
            // Set listener untuk menanggapi klik pada item
            view.setOnClickListener {
                onItemClick?.invoke(listArtikel[adapterPosition])
            }
        }

        fun bindView(artikel: CardArtikelResponse) {
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
