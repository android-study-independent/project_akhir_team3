package com.example.finalproject_chilicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.dataclass.ForumData

class ForumAdapter(private val forumList: List<ForumData>) : RecyclerView.Adapter<ForumAdapter.ForumViewHolder>() {

    class ForumViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val avatar =itemview.findViewById<ImageView>(R.id.imgavatarartikel)

        val profilename= itemview.findViewById<TextView>(R.id.textavatarnameforum)
        val date = itemview.findViewById<TextView>(R.id.datetimeforum)
        val description= itemview.findViewById<TextView>(R.id.txtdescartikelforum)
        val imageartikel = itemview.findViewById<ImageView>(R.id.imgforumartikel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.forum_content,parent,false)
        return  ForumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forumList.size
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val forum = forumList[position]
        holder.avatar.setImageResource(forum.avatar)
        holder.profilename.text = forum.name
        holder.description.text = forum.desc
        holder.date.text = forum.datetime
        holder.imageartikel.setImageResource(forum.image)

    }
}