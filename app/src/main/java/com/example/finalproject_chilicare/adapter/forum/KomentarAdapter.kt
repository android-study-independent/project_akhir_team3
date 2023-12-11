package com.example.finalproject_chilicare.adapter.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.data.response.forum.Komentar

class KomentarAdapter(
    private var listKomentar: List<Komentar>,
    private val forumResponse: ForumResponse
):
    RecyclerView.Adapter<KomentarAdapter.KomentarViewHolder>() {

    inner class KomentarViewHolder(private val itemview : View) : RecyclerView.ViewHolder(itemview){

        fun bindView(komen : Komentar, forumResponse: ForumResponse) {

            val usernameForum= itemview.findViewById<TextView>(R.id.tvNicknamePostingan)
            val dateUploadForum = itemview.findViewById<TextView>(R.id.tvDatePostingan)
            val descriptionForum = itemview.findViewById<TextView>(R.id.tvDescPostingan)
            val jumlahLikeForum = itemview.findViewById<TextView>(R.id.tvLike)
            val jumlahCommentForum = itemview.findViewById<TextView>(R.id.tvComment)

            val forum = forumResponse.forum
            if (forum != null) {
                // Jika objek ForumResponse tidak null, gunakan propertinya
                usernameForum.text = forum.name
                descriptionForum.text = forum.captions
                // Menggunakan data dari ForumResponse
                jumlahLikeForum.text = forumResponse.jumlahLike?.toString() ?: "0"
                jumlahCommentForum.text = forumResponse.jumlahKomentar?.toString() ?: "0"
            } else {
                // Jika objek ForumResponse null, gunakan data dari Komentar
                usernameForum.text = komen.name
                dateUploadForum.text = komen.createdAt
                descriptionForum.text = komen.komentar
                // Menggunakan data dari Komentar
                jumlahCommentForum.text = komen.jumlahKomentar.toString()
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomentarViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.card_comment, parent, false)
        return KomentarViewHolder(view)
    }

    override fun onBindViewHolder(holder: KomentarViewHolder, position: Int) {
        holder.bindView(listKomentar[position], forumResponse)
    }

    override fun getItemCount(): Int {
        return listKomentar.size
    }

}