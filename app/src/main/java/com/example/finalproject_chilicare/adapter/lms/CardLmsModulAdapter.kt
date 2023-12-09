package com.example.finalproject_chilicare.adapter.lms

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.example.finalproject_chilicare.data.response.lms.CardAllLmsResponse
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.ModulMateri
import com.example.finalproject_chilicare.dataclass.ListModulArtikel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class CardLmsModulAdapter(private var listLms:List<ModulMateri>) : RecyclerView.Adapter<CardLmsModulAdapter.cardLmsViewHolder>() {

    var clicklmsModul : ((ModulMateri) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardLmsViewHolder {
       val view : View = LayoutInflater.from(parent.context)
           .inflate(R.layout.card_lms_module,parent,false)
        return cardLmsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listLms.size
    }


    override fun onBindViewHolder(holder: cardLmsViewHolder, position: Int) {
       holder.bindView(listLms[position])

        holder.itemView.setOnClickListener {
            clicklmsModul?.invoke(listLms[position])
        }

    }
    fun updateData(dataNew : List<ModulMateri>) {
        listLms = dataNew
        notifyDataSetChanged()
    }
  inner  class cardLmsViewHolder(private val view : View) : RecyclerView.ViewHolder(view){

      init {
          //melakukan set listener utuk menanggapi klik item
          view.setOnClickListener {
              clicklmsModul?.invoke(listLms[adapterPosition])
          }
      }

      fun bindView(lms : ModulMateri){
          // inisiasi view modul
          val date = view.findViewById<TextView>(R.id.tv_Tanggal)
          val title = view.findViewById<TextView>(R.id.tv_JudulBesar)
          val descShort = view.findViewById<TextView>(R.id.tv_DescSingkat)
          val cover = view.findViewById<ImageView>(R.id.iv_GambarLMS)

          date.text = lms.tanggal
          title.text = lms.judul
          descShort.text = lms.desc

          val path = buildCoverPath(lms.coverPath)

          Picasso.get().load(path).into(cover, object  : Callback{
                  override fun onSuccess() {
                      Log.d("LMS"," image successfuly load")
                  }

                  override fun onError(e: Exception?) {
                      Log.e("LMS","eror load image  ${e?.message}")
                  }
              })
      }
      private  fun buildCoverPath(coverPath : String?): String {
          return coverPath ?:""
      }

    }


}