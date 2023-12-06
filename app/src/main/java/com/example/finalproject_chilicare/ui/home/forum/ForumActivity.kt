package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.ForumAdapter
import com.example.finalproject_chilicare.data.response.forum.ForumResponse

class ForumActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<ForumResponse>
    lateinit var avatarList: Array<Int>
    lateinit var nicknameList: Array<String>
    lateinit var dateList: Array<String>
    lateinit var moreList: Array<Int>
    lateinit var descList: Array<String>
    lateinit var imageList: Array<Int>
    lateinit var ivLikeList: Array<Int>
    lateinit var tvLikeList: Array<String>
    lateinit var ivCommentList: Array<Int>
    lateinit var tvCommentList: Array<String>
    lateinit var ivShareList: Array<Int>
    lateinit var tvShareList: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        // GO PAGES NEW POST
        val ivPlus = findViewById<ImageView>(R.id.ivPlus)
        ivPlus.setOnClickListener {
            Intent(this, NewPostForumActivity::class.java).also {
                startActivity(it)
            }
        }


        avatarList = arrayOf(
            R.drawable.ic_avatar_postingan,
            R.drawable.ic_avatar_postingan,
            R.drawable.ic_avatar_postingan,
            R.drawable.ic_avatar_postingan,
            R.drawable.ic_avatar_postingan,
        )

        nicknameList = arrayOf(
            "Mang Eakkk",
            "Bolehhh",
            "Backburner",
            "Ga bahaya ta?",
            "Mapwuluhhh",
        )

        dateList = arrayOf(
            "2 Desember 2022",
            "3 Desember 2022",
            "4 Desember 2022",
            "5 Desember 2022",
            "6 Desember 2022",
        )

        moreList = arrayOf(
            R.drawable.more,
            R.drawable.more,
            R.drawable.more,
            R.drawable.more,
            R.drawable.more,
        )

        descList = arrayOf(
            "Hallo teman teman online saya, disini saya akan sedikit melakukan sharing hasil dari panen cabai pertama saya, setelah saya belajar dari LMS",
            "Hallo teman teman online saya, disini saya akan sedikit melakukan sharing hasil dari panen cabai pertama saya, setelah saya belajar dari LMS",
            "Hallo teman teman online saya, disini saya akan sedikit melakukan sharing hasil dari panen cabai pertama saya, setelah saya belajar dari LMS",
            "Hallo teman teman online saya, disini saya akan sedikit melakukan sharing hasil dari panen cabai pertama saya, setelah saya belajar dari LMS",
            "Hallo teman teman online saya, disini saya akan sedikit melakukan sharing hasil dari panen cabai pertama saya, setelah saya belajar dari LMS",
        )

        imageList = arrayOf(
            R.drawable.rounded_bg,
            R.drawable.rounded_bg,
            R.drawable.rounded_bg,
            R.drawable.rounded_bg,
            R.drawable.rounded_bg,
        )

        ivLikeList = arrayOf(
            R.drawable.ic_like,
            R.drawable.ic_like,
            R.drawable.ic_like,
            R.drawable.ic_like,
            R.drawable.ic_like,
        )

        tvLikeList = arrayOf(
            "1.000",
            "1.000",
            "1.000",
            "1.000",
            "1.000",
        )

        ivCommentList = arrayOf(
            R.drawable.ic_comment,
            R.drawable.ic_comment,
            R.drawable.ic_comment,
            R.drawable.ic_comment,
            R.drawable.ic_comment,
        )

        tvCommentList = arrayOf(
            "1.000",
            "1.000",
            "1.000",
            "1.000",
            "1.000",
        )

        ivShareList = arrayOf(
            R.drawable.ic_share,
            R.drawable.ic_share,
            R.drawable.ic_share,
            R.drawable.ic_share,
            R.drawable.ic_share,
        )

        tvShareList = arrayOf(
            "Berbagi",
            "Berbagi",
            "Berbagi",
            "Berbagi",
            "Berbagi",
        )


        recyclerView = findViewById(R.id.rvPostingan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        dataList = ArrayList()
        getData()
    }

    private fun getData() {
        for (i in avatarList.indices) {
            val data = ForumResponse(
                avatarList[i],
                nicknameList[i],
                dateList[i],
                moreList[i],
                descList[i],
                imageList[i],
                ivLikeList[i],
                tvLikeList[i],
                ivCommentList[i],
                tvCommentList[i],
                ivShareList[i],
                tvShareList[i],
            )
            dataList.add(data)
        }
        recyclerView.adapter = ForumAdapter(dataList)
    }
}