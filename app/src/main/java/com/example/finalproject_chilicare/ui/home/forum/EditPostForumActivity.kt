package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.EditForumResponse
import com.example.finalproject_chilicare.databinding.ActivityEditPostForumBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditPostForumActivity : AppCompatActivity() {

    lateinit var bindingEditForum : ActivityEditPostForumBinding
    lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post_forum)

        bindingEditForum = DataBindingUtil.setContentView(this, R.layout.activity_edit_post_forum)
        prefHelper = PreferencesHelper.customEditForum(this@EditPostForumActivity)

        // GO PAGES FORUM FOR TESTING
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // GO PAGES FORUM FOR TESTING
//        val btPostingForum = findViewById<Button>(R.id.btnEditPostingForum)
//        btPostingForum.setOnClickListener {
//            Intent(this, ForumActivity::class.java).also {
//                startActivity(it)
//            }
//        }

        bindingEditForum.btnEditPostingForum.setOnClickListener {
            updatePostinganForum(it)
        }



    }

    fun getToken(): String? {

        val prefHelper = PreferencesHelper.customEditForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    fun updatePostinganForum (body : View) {

        val idPostingan = "83"

        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)
        val editCaptions = bindingEditForum.etTextInputEditPostingan.text.toString()

        val captionsRequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), editCaptions)


        getToken()?.let {
            retro.updateCaptions(idPostingan, it, captionsRequestBody).enqueue(object : Callback<EditForumResponse> {
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