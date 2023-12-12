package com.example.finalproject_chilicare.ui.home.forum

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.DeleteForumResponse
import com.example.finalproject_chilicare.databinding.ActivityForumBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ForumActivity : AppCompatActivity() {
//    lateinit var adapterForum = MainForumAdapter()
    lateinit var bindingForum : ActivityForumBinding

    lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_forum)

        bindingForum = DataBindingUtil.setContentView(this, R.layout.activity_forum)
        prefHelper = PreferencesHelper.customPrefForum(this@ForumActivity)

//        adapterForum.setOnItemClickCallback(this)



        // GO PAGES NEW POST
        bindingForum.ivPlus.setOnClickListener {
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

        getToken()?.let {
            apiInterface.getAllForum(it).enqueue(object : Callback<AllForumResponse>{
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
                            setAllForum(it)
                        }
                    }

                    override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {


                    }
                })
            }
        }


        fun getToken(): String? {

            val prefHelper = PreferencesHelper.customPrefForum(this)
            return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
        }


        fun setAllForum(body: AllForumResponse) {
            Log.d("Debug", "Recyler view berhasil -> ${body.allForumItem}")
            val rvPostingan = bindingForum.rvPostingan
            rvPostingan.setHasFixedSize(true)
            rvPostingan.layoutManager = LinearLayoutManager(this)

            val adapter = MainForumAdapter(this, body.allForumItem)

            // Set the adapter to the RecyclerView
            rvPostingan.adapter = adapter
            adapter.setOnItemClickCallback(object : MainForumAdapter.itemClicker {
                override fun onMore(itemForum: AllForumItem, position: Int) {
                    showCustomAlertDialog(this@ForumActivity)
                }

            })
        }

    private fun showCustomAlertDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.card_dialog_forum)
        dialog.setCancelable(true)

        val editPost = dialog.findViewById<TextView>(R.id.textEditPost)
        val deletePost = dialog.findViewById<TextView>(R.id.textDeletePost)
        val cancelDialog = dialog.findViewById<ImageView>(R.id.iconCloseDialog)

        editPost.setOnClickListener {
            val intent = Intent(context, EditPostForumActivity::class.java)
            context.startActivity(intent)
        }

        deletePost.setOnClickListener {
            deletePostingan(it)
        }

        cancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    fun deletePostingan (body: View) {
        val idPostingan = "79"

        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)
        getToken()?.let {
            retro.deletePostingan(idPostingan, it).enqueue(object : Callback<DeleteForumResponse> {
                override fun onResponse(
                    call: Call<DeleteForumResponse>,
                    response: Response<DeleteForumResponse>
                ) {
                    Log.d("Token", "Token -> ${getToken()}")

                    if (response.isSuccessful) {

                        val delResponse = response.body()
                        Log.d("Delete", "Delete berhasil")
                    }
                }

                override fun onFailure(call: Call<DeleteForumResponse>, t: Throwable) {

                    Log.d("Delete", "Failed delete postingan")

                }
            })
        }


    }



}