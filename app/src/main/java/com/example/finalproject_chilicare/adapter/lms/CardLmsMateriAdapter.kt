package com.example.finalproject_chilicare.adapter.lms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import com.example.finalproject_chilicare.ui.lms.MateriLMSActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

class CardLmsMateriAdapter(private var listMateriLms: List<ListMateriLMS>, private val clickListener: ItemClickListener) : RecyclerView.Adapter<CardLmsMateriAdapter.CardMateriHolder>() {


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
        holder.bindItemView(listMateriLms[position])
        val result = listMateriLms[position]
//        holder.titlemateri.text = result.judulMateri
//        holder.descmateri.text = result.shortDesc

        holder.itemView.tag = position
    }

    inner class CardMateriHolder(private val view: View) : RecyclerView.ViewHolder(view){

//        val titlemateri = view.findViewById<TextView>(R.id.tv_MateriLms)
//        val descmateri = view.findViewById<TextView>(R.id.tv_DecMateri)
//        val youtubeLink = view.findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        init {
            itemView.setOnClickListener{


                clickListener.onItemClick(adapterPosition)
            }
        }


        fun bindItemView(cardmateri:ListMateriLMS ) {
            val titlemateri = view.findViewById<TextView>(R.id.tv_MateriLms)
            val descmateri = view.findViewById<TextView>(R.id.tv_DecMateri)
            val link = view.findViewById<YouTubePlayerView>(R.id.youtube_player_view)


            if (link != null) {
                link.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoLink = cardmateri.youtube?.let { extractVideoId(it) }
                        youTubePlayer.loadVideo(videoLink!!, 0f)
                    }
                })
            }


        }
        fun extractVideoId(text: String): String {
            val parts = text.split("/")

            if (text.contains("https://youtu.be/")) {
                return parts[parts.size - 1]
            }

            if (text.contains("https://www.youtube.com/") && text.contains("watch?v=")) {
                return parts[parts.size - 1].replace("watch?v=", "")
            }

            return ""
        }

    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}

