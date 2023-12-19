package com.example.finalproject_chilicare.adapter.forum

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.LikeItem
import com.example.finalproject_chilicare.databinding.CardPostinganBinding
import com.example.finalproject_chilicare.ui.home.forum.DetailPostForumActivity
import com.example.finalproject_chilicare.ui.home.forum.EditPostForumActivity
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

class MainForumAdapter(
    val context: Context,
    var listForum: List<AllForumItem>,
    private val itemClickCallback: ItemClickCallback,
    private val likeClickCallback: LikeClickCallback
) : RecyclerView.Adapter<MainForumAdapter.ForumViewHolder>() {

    interface ItemClickCallback {
        fun onMore(itemForum: AllForumItem, position: Int)
    }

    interface LikeClickCallback {
        fun onLike(itemLike: LikeItem, position: Int)
    }

    fun updateLikeCount(position: Int, newLikeCount: Int) {
        Log.d("MainForumAdapter", "Updating like count at position $position to $newLikeCount")
        listForum.getOrNull(position)?.jumlahLike = newLikeCount
        notifyItemChanged(position)
    }

    inner class ForumViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedForum = listForum[position]

                    // Mengirim data forum ke halaman DetailPostForumActivity
                    val intent = Intent(itemView.context, DetailPostForumActivity::class.java)
                    intent.putExtra("forum_data", clickedForum as Serializable)
                    itemView.context.startActivity(intent)
                }
            }

            itemView.findViewById<ImageView>(R.id.ivLike).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedForum = listForum[position]
                    likeClickCallback.onLike(LikeItem(clickedForum.idUser, clickedForum.forumId.toString()), position)
                }
            }
        }

        val nameUser: TextView = itemView.findViewById(R.id.tvNicknamePostinganForum)
        val dateUploadForum: TextView = itemView.findViewById(R.id.tvDatePostinganForum)
        val descriptionForum: TextView = itemView.findViewById(R.id.tvDescPostinganForum)
        val imageForum: ImageView = itemView.findViewById(R.id.ivGambarPostingan)
        val jumlahLikeForum: TextView = itemView.findViewById(R.id.tvLikeForum)
        val jumlahCommentForum: TextView = itemView.findViewById(R.id.tvCommentForum)
        val iconMode: ImageView = itemView.findViewById(R.id.ivMoreForum)
        val ivLike: ImageView = itemView.findViewById(R.id.ivLike)
    }

    private lateinit var onItemClickCallback: itemClicker
    fun setOnItemClickCallback(onItemClickCallback: itemClicker) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface itemClicker{
        fun onMore (itemForum: AllForumItem, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_postingan, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val listDataItem = listForum[position]

        //format date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date : Date = inputFormat.parse(listDataItem.createdAt)
        val outputFormat = SimpleDateFormat ("yyyy-MM-dd")
        val formatDate = outputFormat.format(date)


        holder.nameUser.text = listDataItem.nameUser
        holder.dateUploadForum.text =  formatDate
        holder.descriptionForum.text = listDataItem.captions
        holder.jumlahLikeForum.text = listDataItem.jumlahLike.toString()
        holder.jumlahCommentForum.text = listDataItem.jumlahKomentar.toString()

        Picasso.get().load(listDataItem.image[0]).into(holder.imageForum)

        holder.iconMode.setOnClickListener {
            onItemClickCallback.onMore(listForum[position], position)
        }
    }

    override fun getItemCount(): Int = listForum.size

}