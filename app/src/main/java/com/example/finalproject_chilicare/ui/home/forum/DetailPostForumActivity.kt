package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.KomentarAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.PostKomentarDetailForumResponse
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.databinding.ActivityDetailPostForumBinding
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailPostForumActivity : AppCompatActivity() {

    private lateinit var adapterForum: KomentarAdapter
    private lateinit var bindingForum: ActivityDetailPostForumBinding
    private lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"

    lateinit var currentForumItem: AllForumItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingForum = DataBindingUtil.setContentView(this, R.layout.activity_detail_post_forum)
        prefHelper = PreferencesHelper.customDetailForum(this@DetailPostForumActivity)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // PENERAPAN POST KOMENTAR
        val balasButton = findViewById<Button>(R.id.balasButton)
        val editText = findViewById<TextView>(R.id.editText)

        balasButton.setOnClickListener {
            val komentarText = editText.text.toString()
            if (komentarText.isNotBlank()) {
                postKomentar(currentForumItem.forumId.toString(), komentarText)
            } else {
                // Handle jika komentar kosong
            }
        }

        val forumData = intent.getSerializableExtra("forum_data") as? AllForumItem
        forumData?.let {
            Log.d("Debug", "forumData is not null: $it")
            currentForumItem = it // Pastikan inisialisasi telah dilakukan di sini
            val usernameForum: TextView = findViewById(R.id.tvNicknamePostingan)
            val dateUploadForum: TextView = findViewById(R.id.tvDatePostingan)
            val descriptionForum: TextView = findViewById(R.id.tvDescPostingan)
            val imageForum: ImageView = findViewById(R.id.iv_Gambar)
            val jumlahLikeForum: TextView = findViewById(R.id.tvLike)
            val jumlahCommentForum: TextView = findViewById(R.id.tvComment)

            usernameForum.text = it.nameUser
            dateUploadForum.text = it.createdAt
            descriptionForum.text = it.captions
            Picasso.get().load(it.image.firstOrNull()).into(imageForum)
            jumlahLikeForum.text = it.jumlahLike.toString()
            jumlahCommentForum.text = it.jumlahKomentar.toString()

            fetchKomentar(it.forumId.toString())
        } ?: Log.d("Debug", "forumData is null")
    }

    private fun fetchKomentar(postinganId: String) {
        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)
        getToken()?.let { apiKey ->
            retro.getKomentar(postinganId).enqueue(object : Callback<ForumResponse> {
                override fun onResponse(
                    call: Call<ForumResponse>,
                    response: Response<ForumResponse>
                ) {
                    if (response.isSuccessful) {
                        val forumResponse = response.body()
                        forumResponse?.let {
                            // Log respons untuk memeriksa data yang diterima
                            Log.d("Debug", "Fetch Komentar Response: $it")
                            setAllForum(it)

                            // Perbarui dataset di adapter
                            adapterForum.updateKomentarList(it.komentars ?: emptyList())
                            // Beri tahu adapter bahwa dataset telah berubah
                            adapterForum.notifyDataSetChanged()
                        }
                    } else {
                        Log.e("Error", "Response not successful: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ForumResponse>, t: Throwable) {
                    Log.e("Error", "Retrofit call failed", t)
                }
            })
        }
    }


    private fun getToken(): String {
        val prefHelper = PreferencesHelper.customDetailForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    private fun setAllForum(body: ForumResponse) {
        Log.d("Debug", "Set All Forum: $body")
        bindingForum.apply {
            adapterForum = KomentarAdapter(body.komentars ?: emptyList(), body)
            val rvForumDetailPost = bindingForum.rvDetailPostinganKomentar

            rvForumDetailPost.layoutManager =
                LinearLayoutManager(this@DetailPostForumActivity, RecyclerView.VERTICAL, false)

            rvForumDetailPost.setHasFixedSize(true)
            rvForumDetailPost.adapter = adapterForum
            adapterForum.notifyDataSetChanged()
        }
    }

    // PENERAPAN POST KOMENTAR
    private fun postKomentar(forumId: String, komentarText: String) {
        Log.d("Debug", "komentarText: $komentarText")
        val retrofit = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)
        getToken()?.let { apiKey ->
            val komentarRequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(),
                "{\"komentar\":\"$komentarText\"}"
            )
            retrofit.postKomentar(forumId, komentarRequestBody)
                .enqueue(object : Callback<PostKomentarDetailForumResponse> {
                    override fun onResponse(
                        call: Call<PostKomentarDetailForumResponse>,
                        response: Response<PostKomentarDetailForumResponse>
                    ) {
                        if (response.isSuccessful) {
                            val postKomentarResponse = response.body()
                            postKomentarResponse?.let {
                                // Handle response setelah mengirim komentar
                                // Log respons untuk memeriksa data yang diterima
                                Log.d("Debug", "Fetch Komentar Response: $it")
                                // Refresh komentar setelah berhasil menambahkan komentar
                                fetchKomentar(forumId)
                            }
                        } else {
                            Log.e("Error", "Response not successful: ${response.message()}")
                        }
                    }

                    override fun onFailure(
                        call: Call<PostKomentarDetailForumResponse>,
                        t: Throwable
                    ) {
                        Log.e("Error", "Retrofit call failed", t)
                    }
                })

        }
    }
}