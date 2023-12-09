package com.example.finalproject_chilicare.adapter.forum

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.squareup.picasso.Picasso

class MainForumAdapter(private var listForum: List<AllForumItem>):
    RecyclerView.Adapter<MainForumAdapter.ForumViewHolder>() {

    inner class ForumViewHolder(private val itemview : View) : RecyclerView.ViewHolder(itemview){

        fun bindView(forum : AllForumItem) {

            //val avatarForum =itemview.findViewById<ImageView>(R.id.ivAvatarPostingan)
            val usernameForum= itemview.findViewById<TextView>(R.id.tvNicknamePostingan)
            val dateUploadForum = itemview.findViewById<TextView>(R.id.tvDatePostingan)
            val descriptionForum = itemview.findViewById<TextView>(R.id.tvDescPostingan)
            val imageForum = itemview.findViewById<ImageView>(R.id.ivGambarPostingan)
            val jumlahLikeForum = itemview.findViewById<TextView>(R.id.tvLike)
            val jumlahCommentForum = itemview.findViewById<TextView>(R.id.tvComment)

            usernameForum.text = forum.nameUser
            dateUploadForum.text = forum.createdAt
            descriptionForum.text = forum.captions
            jumlahLikeForum.text = forum.jumlahLike.toString()
            jumlahCommentForum.text = forum.jumlahKomentar.toString()

            val imagePath = buildImageForum(forum.image, 0)

            if (imagePath.isNotEmpty()) {
                Picasso.get().load(imagePath).into(imageForum)
                Log.d("Forum Image", imagePath)
            } else {
                Log.e("Forum Image", "Image not found at index 0")
            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.card_postingan, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        holder.bindView(listForum[position])
    }

    override fun getItemCount(): Int {
        return listForum.size
    }

    fun updateData(newDataForum: List<AllForumItem>) {
        listForum = newDataForum
        notifyDataSetChanged()
    }



    private fun buildImageForum(imageForum: List<String>, index: Int): String {
        return imageForum.getOrNull(index) ?: ""
    }
}