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
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.squareup.picasso.Picasso

class MainForumAdapter(private val listForum: List<AllForumItem>):
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

            usernameForum.text = forum.name_user
            dateUploadForum.text = forum.createdAt
            descriptionForum.text = forum.captions
            jumlahLikeForum.text = forum.jumlahLike.toString()
            jumlahCommentForum.text = forum.jumlahKomentar.toString()

            val path = buildImageForum(forum.image.toString())
            Picasso.get().load(path).into(imageForum)
            Log.d("Forum Image",path)


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



    private fun buildImageForum(imageForum: String?): String {
        return imageForum ?: ""
    }
}