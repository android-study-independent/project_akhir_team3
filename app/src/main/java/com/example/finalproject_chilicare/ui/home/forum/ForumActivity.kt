package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.databinding.ActivityForumBinding
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//@AndroidEntryPoint
class ForumActivity : AppCompatActivity() {
    lateinit var bindingForum : ActivityForumBinding
    lateinit var adapter: MainForumAdapter

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

    companion object {
        lateinit var apiInterface : ApiInterface
            private set
    }

    lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forum)

        bindingForum = DataBindingUtil.setContentView(this, R.layout.activity_forum)
        prefHelper = PreferencesHelper.customPrefForum(this@ForumActivity)


        // GO PAGES NEW POST
        val ivPlus = findViewById<ImageView>(R.id.ivPlus)
        ivPlus.setOnClickListener {
            Intent(this, NewPostForumActivity::class.java).also {
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

        apiInterface.getAllForum().enqueue(object : Callback<AllForumResponse>{
            override fun onResponse(
                call: Call<AllForumResponse>,
                response: Response<AllForumResponse>
            ) {
                if (prefHelper.getBoolean(PreferencesHelper.KEY_IS_LOGIN, false)) {
                    forumList()

                }
            }

            override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {

            }

        })

    }

    private  fun forumList () {

        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)
        retro.getAllForum().enqueue(object : Callback<AllForumResponse>{
            override fun onResponse(
                call: Call<AllForumResponse>,
                response: Response<AllForumResponse>
            ) {
                response.body()?.let {
                    setAllForum(it)
                }
            }

            override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {

            }

        })


    }


    private fun getToken() : String? {

        val prefHelper = PreferencesHelper.customPrefForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }



    private fun setAllForum (body : AllForumResponse) {
        Log.d("Debug", "Recyler view berhasil -> ${body.allForumResponse}")


        bindingForum.apply {



            adapter = MainForumAdapter(body.allForumResponse)

            val rvForum = bindingForum.rvPostingan

            rvForum.layoutManager = LinearLayoutManager(this@ForumActivity, RecyclerView.VERTICAL, false)

            rvForum.setHasFixedSize(true)


            rvForum.adapter = adapter


            adapter.notifyDataSetChanged()
            Log.d("Debug", "Recyler view berhasil -> ${body.allForumResponse}")


        }



    }

}