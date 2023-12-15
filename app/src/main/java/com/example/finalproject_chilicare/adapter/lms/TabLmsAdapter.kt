package com.example.finalproject_chilicare.adapter.lms

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.response.lms.ModulMateri
import com.example.finalproject_chilicare.data.response.lms.TabLmsResponse
import com.example.finalproject_chilicare.utils.OnTabClickListener

class TabLmsAdapter(
    private val statuslist: ArrayList<TabLmsResponse>,
    private val listener: OnTabClickListener,

) : RecyclerView.Adapter<TabLmsAdapter.TabViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tab_lms, parent, false)
        return TabViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return statuslist.size
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        val currentItem = statuslist[position]
        holder.rvStatus.text = currentItem.status

    }


    class TabViewHolder(itemView: View, private val listener: OnTabClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val rvStatus: TextView = itemView.findViewById(R.id.tvstatus)

        init {
            itemView.setOnClickListener {
                listener.onTabClick(rvStatus.text.toString())
                val isPressed = false

                if(isPressed){
                    it.setBackgroundColor(Color.TRANSPARENT)
                } else{
                    it.setBackgroundColor(Color.parseColor("#BF2A63"))
                }


            }
            itemView.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        view.setBackgroundColor(Color.parseColor("#BF2A63"))
                    }

                    MotionEvent.ACTION_UP -> {
                        view.setBackgroundColor(Color.TRANSPARENT)
                    }
                }
                false
            }
        }
    }
}