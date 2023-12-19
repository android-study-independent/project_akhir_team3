package com.example.finalproject_chilicare.ui.home.forum

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.forum.KomentarAdapter
import com.example.finalproject_chilicare.adapter.forum.MainForumAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.AllForumItem
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.DeleteForumResponse
import com.example.finalproject_chilicare.data.models.LikeForumResponse
import com.example.finalproject_chilicare.data.models.LikeItem
import com.example.finalproject_chilicare.data.models.PostKomentarDetailForumResponse
import com.example.finalproject_chilicare.data.models.UnlikeResponse
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.data.response.forum.Komentar
import com.example.finalproject_chilicare.databinding.ActivityDetailPostForumBinding
import com.example.finalproject_chilicare.databinding.ActivityForumBinding
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

class DetailPostForumActivity : AppCompatActivity() {


    private lateinit var adapterKomentarForum: KomentarAdapter
    private lateinit var bindingDetailForum: ActivityDetailPostForumBinding
    private lateinit var prefHelper: SharedPreferences
    private val baseUrl = "http://195.35.32.179:8003/"

    // UNTUK KOMENTAR
    private var komentarCallback: KomentarCallback? = null
    lateinit var currentForumItem: AllForumItem

    //  CALL ID XML
    lateinit var ivBack: ImageView
    lateinit var balasButton: Button
    lateinit var editText: EditText
    lateinit var usernameForum: TextView
    lateinit var dateUploadForum: TextView
    lateinit var descriptionForum: TextView
    lateinit var imageForum: ImageView
    lateinit var jumlahLikeForum: TextView
    lateinit var jumlahCommentForum: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // INISASI BINDING
        bindingDetailForum = DataBindingUtil.setContentView(this, R.layout.activity_detail_post_forum)
        prefHelper = PreferencesHelper.customDetailForum(this@DetailPostForumActivity)

        // PEMANGILAN ID XML
        ivBack = findViewById(R.id.ivBack)
        balasButton = findViewById(R.id.balasButton)
        editText = findViewById(R.id.editTextPostKomentar)

        // NAVIGATE FORUM ACTIVITY
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        // BALAS BUTTON LOGIC
        balasButton.setOnClickListener {
            val komentarText = editText.text.toString()
            if (komentarText.isNotBlank()) {
                postKomentar(currentForumItem.forumId.toString(), komentarText)

                hideKeyboard()
                editText.text = null
            }
        }

        // GET DATA DARI INTENT (FORUMACTIVITY)
        val intentData = intent.getSerializableExtra("forum_data") as? AllForumItem
        intentData?.let {
            Log.d("Debug", "forumData is not null: $it")
            currentForumItem = it // Assign it to the lateinit var

            // Initialize the TextViews and ImageView
            usernameForum = findViewById(R.id.tvNicknamePostinganDetail)
            dateUploadForum = findViewById(R.id.tvDatePostinganDetail)
            descriptionForum = findViewById(R.id.tvDescPostinganDetail)
            imageForum = findViewById(R.id.ivGambarPostingaDetail)
            jumlahLikeForum = findViewById(R.id.tvLikePostinganDetail)
            jumlahCommentForum = findViewById(R.id.tvCommentPostinganDetail)

            //format date
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date : Date = inputFormat.parse(it.createdAt)
            val outputFormat = SimpleDateFormat ("yyyy-MM-dd")
            val formatDate = outputFormat.format(date)

            // Rest of your code remains the same
            usernameForum.text = it.nameUser
            dateUploadForum.text = formatDate
            descriptionForum.text = it.captions
            Picasso.get().load(it.image.firstOrNull()).into(imageForum)
            jumlahLikeForum.text = it.jumlahLike.toString()
            jumlahCommentForum.text = it.jumlahKomentar.toString()

            fetchKomentar(it.forumId.toString())
        } ?: Log.d("Debug", "forumData is null")

        // BINDING NAVIGATE DETAIL POST AND MENAMPILKAN FUNCTION SHOW CUSTOM ALERT DIALOG
        bindingDetailForum.ivMoreInDetail.setOnClickListener {
            showCustomAlertDialog(this@DetailPostForumActivity, currentForumItem)
        }

        // CALL FUNCTION FETCH KOMENTAR
        fetchKomentar(currentForumItem.forumId.toString())
    }

    // GET TOKEN
    private fun getToken(): String {
        val prefHelper = PreferencesHelper.customDetailForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    // FETCH KOMENTAR
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

                            // Perbarui tampilan komentar di adapter
                            val komentars = it.komentars ?: emptyList()
                            adapterKomentarForum.updateKomentarList(komentars.map { komentar ->
                                val isiKomentar = komentar.komentar
                                Log.d("Debug", "isiKomentar: $isiKomentar")
                                // Mengembalikan objek Komentar yang sudah dimodifikasi
                                komentar
                            })

                            // Panggil callback jika ada komentar baru
                            komentarCallback?.onKomentarAdded(komentars)
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

    // SET ALL FORUM
    private fun setAllForum(body: ForumResponse) {
        Log.d("Debug", "Set All Forum: $body")
        bindingDetailForum.apply {
            adapterKomentarForum = KomentarAdapter(body.komentars ?: emptyList(), body)
            val rvForumDetailPost = bindingDetailForum.rvDetailPostinganKomentar

            rvForumDetailPost.layoutManager =
                LinearLayoutManager(this@DetailPostForumActivity, RecyclerView.VERTICAL, false)

            rvForumDetailPost.setHasFixedSize(true)
            rvForumDetailPost.adapter = adapterKomentarForum

            adapterKomentarForum.notifyDataSetChanged()

        }
    }

    // SHOW CUSTOM ALERT DIALOG
    private fun showCustomAlertDialog(context: Context, data: AllForumItem) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.card_dialog_forum)
        dialog.setCancelable(true)

        val editPost = dialog.findViewById<TextView>(R.id.textEditPost)
        val deletePost = dialog.findViewById<TextView>(R.id.textDeletePost)
        val cancelDialog = dialog.findViewById<ImageView>(R.id.iconCloseDialog)

        editPost.setOnClickListener {
            Intent(this, EditPostForumActivity::class.java).also {
                val intent = Intent(this, EditPostForumActivity::class.java)
                intent.putExtra("forumId", data.forumId)
                Log.d("forumId", "${data.forumId}")
                startActivity(it)
                startActivity(intent)
                Log.d("forumId", "${data.forumId}")
            }
        }

        deletePost.setOnClickListener {
            deletePostingan(it, data.forumId.toString())
        }

        cancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    // DELETE POSTINGAN
    fun deletePostingan(body: View, data: String) {
        val idPostingan = data

        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)

        retro.deletePostingan(idPostingan).enqueue(object : Callback<DeleteForumResponse> {
            override fun onResponse(
                call: Call<DeleteForumResponse>,
                response: Response<DeleteForumResponse>
            ) {

                Log.d("Token", "Token -> ")

                if (response.isSuccessful) {

                    val delResponse = response.body()
                    Log.d("Delete", "Delete berhasil")
                    Toast.makeText(
                        this@DetailPostForumActivity,
                        "User berhasil menghapus postingan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DetailPostForumActivity,
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

    // POST KOMENTAR
    private fun postKomentar(forumId: String, komentarText: String, context: Context = this) {
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
                                // Tampilkan komentar baru di tampilan
                                fetchKomentar(forumId)
                                showToast(context, "Komentar Anda berhasil ditambahkan.", Toast.LENGTH_LONG)
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

    // KOMENTAR CALLBACK
    interface KomentarCallback {
        fun onKomentarAdded(komentars: List<Komentar>) {
            // Handle komentar baru yang ditambahkan ke data class Komentar
            for (komentar in komentars) {
                val isiKomentar = komentar.komentar
                // Lakukan sesuatu dengan komentar baru
                Log.d("Debug", "Komentar baru: $isiKomentar")
            }
        }
    }

    // SHOW TOAST SEMENTARA UNTUK KOMENTAR DITAMBAHKAN, KALO MO DIGUNAKAN DI FUNCTION LAINNYA SILAHKAN
    private fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }

    // MENYEMBUNYIKAN KEYABOARD
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}