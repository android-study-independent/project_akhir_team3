package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class DetailPostForumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post_forum)

        // GO PAGES FORUM FOR TESTING
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // GO EDIT FORUM FOR TESTING
        val balasButton = findViewById<Button>(R.id.balasButton)
        balasButton.setOnClickListener {
            Intent(this, EditPostForumActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}