package com.example.finalproject_chilicare.adapter.forum

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.databinding.CardPostinganBinding
import com.example.finalproject_chilicare.ui.home.forum.DetailPostForumActivity
import com.example.finalproject_chilicare.ui.home.forum.EditPostForumActivity
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.io.Serializable

class MainForumAdapter(val context: Context, private var listForum: List<AllForumItem>) :
    RecyclerView.Adapter<MainForumAdapter.ForumViewHolder>() {

    inner class ForumViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                // Mendapatkan posisi item yang diklik
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Mendapatkan data forum sesuai posisi yang diklik
                    val clickedForum = listForum[position]

                    // Mengirim data forum ke halaman DetailPostForumActivity
                    val intent = Intent(itemView.context, DetailPostForumActivity::class.java)
                    intent.putExtra("forum_data", clickedForum as Serializable)
                    itemView.context.startActivity(intent)
                }
            }
        }



        val nameUser = itemView.findViewById<TextView>(R.id.tvNicknamePostinganForum)
        val dateUploadForum =itemView.findViewById<TextView>(R.id.tvDatePostinganForum)
        val descriptionForum = itemView.findViewById<TextView>(R.id.tvDescPostinganForum)
        val imageForum = itemView.findViewById<ImageView>(R.id.ivGambarPostingan)
        val jumlahLikeForum = itemView.findViewById<TextView>(R.id.tvLikeForum)
        val jumlahCommentForum = itemView.findViewById<TextView>(R.id.tvCommentForum)
        val iconMode = itemView.findViewById<ImageView>(R.id.ivMoreForum)
    }

    private lateinit var onItemClickCallback: itemClicker

    fun setOnItemClickCallback(onItemClickCallback: itemClicker) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface itemClicker{
        fun onMore (itemForum: AllForumItem, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_postingan, parent, false)
        return ForumViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val listDataItem = listForum[position]

        holder.nameUser.text = listDataItem.nameUser
        holder.dateUploadForum.text = listDataItem.createdAt
        holder.descriptionForum.text = listDataItem.captions
        holder.jumlahLikeForum.text = listDataItem.jumlahLike.toString()
        holder.jumlahCommentForum.text = listDataItem.jumlahKomentar.toString()

        Picasso.get().load(listDataItem.image[0]).into(holder.imageForum)


        holder.iconMode.setOnClickListener {
            onItemClickCallback.onMore(listForum[position], position)
        }
    }

    override fun getItemCount() = listForum.size
}


