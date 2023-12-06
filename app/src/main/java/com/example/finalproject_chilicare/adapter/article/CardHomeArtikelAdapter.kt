package com.example.finalproject_chilicare.adapter.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.dataclass.HomeArtikel

class CardHomeArtikelAdapter (private val listArtikelHome: List<HomeArtikel>) : RecyclerView.Adapter<CardHomeArtikelAdapter.ArtikelHomeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelHomeHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.card_artikelhomepage,parent,false)
        return ArtikelHomeHolder(view)
    }

    override fun getItemCount(): Int {
        return listArtikelHome.size
    }

    override fun onBindViewHolder(holder: ArtikelHomeHolder, position: Int) {
      val artikel = listArtikelHome[position]
        holder.category.text = artikel.category
        holder.title.text = artikel.title
        holder.desc.text = artikel.decs
        holder.readTime.text = artikel.readTime
        holder.cover.setImageResource(artikel.cover)
//        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(listArtikelHome[position])
//        }

    }
class ArtikelHomeHolder (private val view : View) : RecyclerView.ViewHolder(view){
    val category = view. findViewById<TextView>(R.id.titleMenanam)
    val title = view.findViewById<TextView>(R.id.txttitledesc)
    val desc = view.findViewById<TextView>(R.id.txtdescmenanam)
    val readTime = view.findViewById<TextView>(R.id.tvlastseen)
    val cover = view.findViewById<ImageView>(R.id.tvartikel)
}

//        fun bindView(artikel:HomeArtikel){
//
//            val category = view. findViewById<TextView>(R.id.titleMenanam)
//            val title = view.findViewById<TextView>(R.id.txttitledesc)
//            val desc = view.findViewById<TextView>(R.id.txtdescmenanam)
//            val readTime = view.findViewById<TextView>(R.id.tvlastseen)
//            val cover = view.findViewById<ImageView>(R.id.tvartikel)
//
//            category.text = artikel.category
//            title.text = artikel.title
//            desc.text = artikel.desc
//            readTime.text = artikel.readTime
//
//            val path = buildCoverPath(artikel.coverPath)
//
//            Picasso.get().load(path).into(cover, object : Callback {
//                override fun onSuccess() {
//                    Log.d("Picasso", "Image loaded successfully")
//                }
//
//                override fun onError(e: Exception?) {
//                    Log.e("Picasso", "Error loading image: ${e?.message}")
//                }
//            })
//
//        }
//        private fun buildCoverPath(cover : String?) : String {
//            return cover ?: ""
//        }
//    }
}