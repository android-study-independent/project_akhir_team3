package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class NewPostForumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_forum)

        // GO PAGES FORUM
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // GO PAGES DETAIL FORUM
        val btPostingForum = findViewById<Button>(R.id.btPostingForum)
        btPostingForum.setOnClickListener {
            Intent(this, DetailPostForumActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}