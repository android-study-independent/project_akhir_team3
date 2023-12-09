package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewPostForumActivity : AppCompatActivity() {

    private val baseUrl = "http://195.35.32.179:8003/"
    lateinit var prefHelper: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_forum)

        prefHelper = PreferencesHelper.customAddForum(this)

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

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = getToken()
                Log.d("Token", "dapat token dari login -> $token")
                val request = chain.request()
                    .newBuilder()
                    .addHeader("x-api-key", "$token")
                    .build()
                chain.proceed(request)

            }
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)

        getToken()?.let {
            apiInterface.getAllForum(it).enqueue(object : Callback<AllForumResponse> {
                override fun onResponse(
                    call: Call<AllForumResponse>,
                    response: Response<AllForumResponse>
                ) {

                    forumList()

                    if (prefHelper.getBoolean(PreferencesHelper.KEY_IS_LOGIN, false)) {

                    }
                }

                override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {
                    Log.e("Error", "Retrofit call failed", t)
                }

            })
        }





    }

    fun forumList() {

        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)
        getToken()?.let {
            retro.getAllForum(it).enqueue(object : Callback<AllForumResponse> {
                override fun onResponse(
                    call: Call<AllForumResponse>,
                    response: Response<AllForumResponse>
                ) {
                    response.body()?.let {

                    }
                }

                override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {

                }

            })
        }
    }



    fun getToken(): String? {

        val prefHelper = PreferencesHelper.customAddForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }
}