package com.example.finalproject_chilicare.ui.home.forum

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.DeleteForumResponse
import com.example.finalproject_chilicare.data.models.LikeForumResponse
import com.example.finalproject_chilicare.data.models.LikeItem
import com.example.finalproject_chilicare.data.models.UnlikeResponse
import com.example.finalproject_chilicare.databinding.ActivityForumBinding
import com.example.finalproject_chilicare.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumActivity : AppCompatActivity() {

    private lateinit var bindingForum: ActivityForumBinding
    private lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INISIASI BINDING
        bindingForum = DataBindingUtil.setContentView(this, R.layout.activity_forum)
        prefHelper = PreferencesHelper.customPrefForum(this@ForumActivity)

        // NAVIGATE NEW POST ACTIVITY
        bindingForum.ivPlus.setOnClickListener {
            Intent(this, NewPostForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // NAVIAGTE HOME ACTIVITY
        bindingForum.ivBackToHome.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }

        // CALL FORUM LIST
        forumList()
    }

    // FORUM LIST
    fun forumList() {

        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)
        retro.getAllForum().enqueue(object : Callback<AllForumResponse> {
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

    // GET TOKEN
    private fun getToken(): String {
        val prefHelper = PreferencesHelper.customPrefForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    // SET ALL FORUM
    fun setAllForum(body: AllForumResponse) {
        Log.d("Debugs", "Recyler view berhasil -> ${body.allForumItem}")
        val rvPostingan = bindingForum.rvPostingan
        rvPostingan.setHasFixedSize(true)
        rvPostingan.layoutManager = LinearLayoutManager(this)
        val adapter = MainForumAdapter(this, body.allForumItem, itemClickCallback, likeClickCallback)

        // Set the adapter to the RecyclerView
        rvPostingan.adapter = adapter

        adapter.setOnItemClickCallback(object : MainForumAdapter.itemClicker {
            override fun onMore(itemForum: AllForumItem, position: Int) {
                showCustomAlertDialog(this@ForumActivity, itemForum)
            }
        })

        val itemClickCallback = object : MainForumAdapter.ItemClickCallback {
            override fun onMore(itemForum: AllForumItem, position: Int) {
                showCustomAlertDialog(this@ForumActivity, itemForum)
            }
        }

        val likeClickCallback = object : MainForumAdapter.LikeClickCallback {
            override fun onLike(itemLike: LikeItem, position: Int) {
                likePostingan(itemLike, position)
            }
        }
    }

    // ITEM CLICKCALLBACK
    private val itemClickCallback = object : MainForumAdapter.ItemClickCallback {
        override fun onMore(itemForum: AllForumItem, position: Int) {
            showCustomAlertDialog(this@ForumActivity, itemForum)
        }
    }

    // LIKE CLICKCALLBACK
    private val likeClickCallback = object : MainForumAdapter.LikeClickCallback {
        override fun onLike(itemLike: LikeItem, position: Int) {
            likePostingan(itemLike, position)
        }
    }

    // SHOW DIALOG
    private fun showCustomAlertDialog(context: Context, data: AllForumItem) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.card_dialog_forum)
        dialog.setCancelable(true)

        val editPost = dialog.findViewById<TextView>(R.id.textEditPost)
        val deletePost = dialog.findViewById<TextView>(R.id.textDeletePost)
        val cancelDialog = dialog.findViewById<ImageView>(R.id.iconCloseDialog)

        editPost.setOnClickListener {
            Intent(this, EditPostForumActivity::class.java).also {
                it.putExtra("forumId", data.forumId)
                startActivity(it)
            }
        }

        deletePost.setOnClickListener {
            deletePostingan(data.forumId.toString())
        }

        cancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // DELETE POSTINGAN
    private fun deletePostingan(data: String) {
        val idPostingan = data
        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)

        getToken()?.let {
            retro.deletePostingan(idPostingan).enqueue(object : Callback<DeleteForumResponse> {
                override fun onResponse(
                    call: Call<DeleteForumResponse>,
                    response: Response<DeleteForumResponse>
                ) {
                    Log.d("Token", "Token -> ${getToken()}")
                    if (response.isSuccessful) {
                        val delResponse = response.body()
                        Log.d("Delete", "Delete berhasil")
                        Toast.makeText(
                            this@ForumActivity,
                            "User berhasil menghapus postingan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@ForumActivity,
                            "User tidak memiliki akses delete postingan ini",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DeleteForumResponse>, t: Throwable) {
                    Log.d("Delete", "Failed delete postingan")
                }
            })
        }
    }

    // LIKE POSTINGAN
    private fun likePostingan(data: LikeItem, position: Int, context: Context = this) {
        val idPostingan = data.liked_by
        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)

        getToken()?.let {
            retro.likePostingan(idPostingan).enqueue(object : Callback<LikeForumResponse> {
                override fun onResponse(
                    call: Call<LikeForumResponse>,
                    response: Response<LikeForumResponse>
                ) {
                    Log.d("LikePostingan", "Response Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val likeResponse = response.body()

                        val adapter = bindingForum.rvPostingan.adapter as? MainForumAdapter
                        adapter?.let {
                            val likedForum = it.listForum.getOrNull(position)
                            likedForum?.like()
                            it.updateLikeCount(position, likedForum?.jumlahLike ?: 0)

                            // Notify the adapter that the dataset has changed
                            it.notifyDataSetChanged()

                            showToast(context, "Anda baru saja menyukai postingan ini", Toast.LENGTH_LONG)
                        }
                    } else {
                        unlikePostingan(idPostingan, position)
                    }
                }

                override fun onFailure(call: Call<LikeForumResponse>, t: Throwable) {

                }
            })
        }
    }

    // UNLIKE POSTINGAN
    private fun unlikePostingan(idPostingan: String, position: Int, context: Context = this) {
        val retro = Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)

        getToken()?.let {
            retro.unlikePostingan(idPostingan).enqueue(object : Callback<UnlikeResponse> {
                override fun onResponse(
                    call: Call<UnlikeResponse>,
                    response: Response<UnlikeResponse>
                ) {
                    Log.d("UnlikePostingan", "Response Code: ${response.code()}")
                    if (response.isSuccessful) {
                        val adapter = bindingForum.rvPostingan.adapter as? MainForumAdapter
                        adapter?.let {
                            val unlikedForum = it.listForum.getOrNull(position)
                            unlikedForum?.unlike()
                            it.updateLikeCount(position, unlikedForum?.jumlahLike ?: 0)

                            // Notify the adapter that the dataset has changed
                            it.notifyDataSetChanged()

                            showToast(context, "Anda sudah berhasil unlike postingan ini.", Toast.LENGTH_LONG)
                        }
                    }
                }

                override fun onFailure(call: Call<UnlikeResponse>, t: Throwable) {
                    // handle toast or notifikasi ketika berhasil apa tidak
                }
            })
        }
    }

    // SHOW TOAST SEMENTARA UNTUK LIKE DAN UNLIKE SAJA, KALO MO DIGUNAKAN DI FUNCTION LAINNYA SILAHKAN
    private fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }

}
