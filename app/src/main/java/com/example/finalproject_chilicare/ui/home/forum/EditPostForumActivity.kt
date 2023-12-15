package com.example.finalproject_chilicare.ui.home.forum

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.EditForumResponse
import com.example.finalproject_chilicare.databinding.ActivityEditPostForumBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPostForumActivity : AppCompatActivity() {

    lateinit var bindingEditForum : ActivityEditPostForumBinding
    lateinit var prefHelper: SharedPreferences
    private lateinit var context: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post_forum)



        Log.d("Activity", "Berhasil pindah ke Edit Postingan")

        bindingEditForum = DataBindingUtil.setContentView(this, R.layout.activity_edit_post_forum)
        prefHelper = PreferencesHelper.customEditForum(this@EditPostForumActivity)

        val intent = intent.getIntExtra("forumId", 0)
        Log.d("forumdId", "$intent")



        //val forumList = AllForumItem(listForum.forumId.toString())

        // GO PAGES FORUM FOR TESTING
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }


        bindingEditForum.btnEditPostingForum.setOnClickListener {
            updatePostinganForum(it)
            val intent = Intent(this@EditPostForumActivity, ForumActivity::class.java)
            startActivity(intent)
        }
    }

    fun getToken(): String {
        val prefHelper = PreferencesHelper.customEditForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    fun updatePostinganForum (data: View) {

        val idPostingan = intent.getIntExtra("forumId", 0)
        Log.d("forumdId", "$idPostingan")


        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)
        val editCaptions = bindingEditForum.etTextInputEditPostingan.text.toString()

        val captionsRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), editCaptions)


        getToken()?.let {
            retro.updateCaptions(idPostingan.toString(), captionsRequestBody).enqueue(object : Callback<EditForumResponse> {
                override fun onResponse(
                    call: Call<EditForumResponse>,
                    response: Response<EditForumResponse>
                ) {

                    Log.d("Token", "Token -> ${getToken()}")

                    if (response.isSuccessful) {

                        val editResponse = response.body()
                        Log.d("Delete", "Delete berhasil")
                    }
                }

                override fun onFailure(call: Call<EditForumResponse>, t: Throwable) {

                    Log.d("Delete", "Failed delete postingan")

                }
            })
        }

    }

}