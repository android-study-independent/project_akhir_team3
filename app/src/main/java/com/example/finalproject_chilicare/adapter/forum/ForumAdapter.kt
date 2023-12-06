package com.example.finalproject_chilicare.adapter.forum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.forum.ForumResponse

class ForumAdapter(private val dataList: ArrayList<ForumResponse>):
    RecyclerView.Adapter<ForumAdapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_postingan, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.ivAvatarPostingan.setImageResource(currentItem.avatar!!)
        holder.tvNicknamePostingan.text = currentItem.nickname
        holder.tvDatePostingan.text = currentItem.date
        holder.ivMore.setImageResource(currentItem.more!!)
        holder.tvDescPostingan.text = currentItem.desc
        holder.iv_Gambar.setImageResource(currentItem.image!!)
        holder.ivLike.setImageResource(currentItem.ivLike!!)
        holder.tvLike.text = currentItem.tvLike
        holder.ivComment.setImageResource(currentItem.ivComment!!)
        holder.tvComment.text = currentItem.tvComment
        holder.ivShare.setImageResource(currentItem.ivShare!!)
        holder.tvShare.text = currentItem.tvShare
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivAvatarPostingan: ImageView = itemView.findViewById(R.id.ivAvatarPostingan)
        val tvNicknamePostingan: TextView = itemView.findViewById(R.id.tvNicknamePostingan)
        val tvDatePostingan: TextView = itemView.findViewById(R.id.tvDatePostingan)
        val ivMore: ImageView = itemView.findViewById(R.id.ivMore)
        val tvDescPostingan: TextView = itemView.findViewById(R.id.tvDescPostingan)
        val iv_Gambar: ImageView = itemView.findViewById(R.id.iv_Gambar)
        val ivLike: ImageView = itemView.findViewById(R.id.ivLike)
        val tvLike: TextView = itemView.findViewById(R.id.tvLike)
        val ivComment: ImageView = itemView.findViewById(R.id.ivComment)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        val ivShare: ImageView = itemView.findViewById(R.id.ivShare)
        val tvShare: TextView = itemView.findViewById(R.id.tvShare)
    }
}