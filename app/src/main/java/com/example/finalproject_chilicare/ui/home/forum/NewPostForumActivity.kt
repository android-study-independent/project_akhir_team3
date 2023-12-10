package com.example.finalproject_chilicare.ui.home.forum

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.PreferencesHelper.IMAGE_REQUEST
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.models.AddNewForumResponse
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.CreateForumResponse
import com.example.finalproject_chilicare.databinding.ActivityNewPostForumBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class NewPostForumActivity : AppCompatActivity() {

    private val baseUrl = "http://195.35.32.179:8003/"
    lateinit var prefHelper: SharedPreferences

    private lateinit var bindingPostForum : ActivityNewPostForumBinding
    lateinit var uploadImage : ImageView
    lateinit var checkImageUpload1 : ImageView
    lateinit var checkImageUpload2 : ImageView
    lateinit var checkImageUpload3 : ImageView
    lateinit var checkImageUpload4 : ImageView
    lateinit var inputPostingan : EditText
    lateinit var btnUploadPostingan : Button
    private var path: String? = null
    private val selectedImageUris = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_forum)

       // bindingPostForum = DataBindingUtil.setContentView(this@NewPostForumActivity, R.layout.activity_new_post_forum)

        prefHelper = PreferencesHelper.customAddForum(this)

        // GO PAGES FORUM
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        path = "default/path"
        uploadImage = findViewById(R.id.ivMedia)
        checkImageUpload1 = findViewById(R.id.checkImageUpload1)
        checkImageUpload2 = findViewById(R.id.checkImageUpload2)
        checkImageUpload3 = findViewById(R.id.checkImageUpload3)
        checkImageUpload4 = findViewById(R.id.checkImageUpload4)
        inputPostingan = findViewById(R.id.etTextInput)
        btnUploadPostingan = findViewById(R.id.btPostingForum)

        uploadImage.setOnClickListener {
            uploadImage()
        }

        // GO PAGES DETAIL FORUM
//        val btPostingForum = findViewById<Button>(R.id.btPostingForum)
//        btPostingForum.setOnClickListener {
//
//            Intent(this, DetailPostForumActivity::class.java).also {
//                startActivity(it)
//            }
//        }
        btnUploadPostingan.setOnClickListener {
            addForum(inputPostingan.text.toString())
        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val token = getToken()
//                Log.d("Token", "dapat token dari login -> $token")
//                val request = chain.request()
//                    .newBuilder()
//                    .addHeader("x-api-key", "$token")
//                    .build()
//                chain.proceed(request)
//
//            }
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiInterface = retrofit.create(ApiInterface::class.java)
//
//        getToken()?.let {
//            apiInterface.getAllForum(it).enqueue(object : Callback<AllForumResponse> {
//                override fun onResponse(
//                    call: Call<AllForumResponse>,
//                    response: Response<AllForumResponse>
//                ) {
//
//
//
//                }
//
//                override fun onFailure(call: Call<AllForumResponse>, t: Throwable) {
//                    Log.e("Error", "Retrofit call failed", t)
//                }
//
//            })
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val clipData = data.clipData

            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val selectedImageUri: Uri = clipData.getItemAt(i).uri
                    selectedImageUris.add(selectedImageUri)
                    displaySelectedImage(selectedImageUri, i)
                }
            } else if (data.data != null) {
                val selectedImageUri: Uri = data.data!!
                selectedImageUris.add(selectedImageUri)
                displaySelectedImage(selectedImageUri, selectedImageUris.size - 1)
            }
        }
    }

    private fun displaySelectedImage(imageUri: Uri, position: Int) {
        when (position) {
            0 -> checkImageUpload1.setImageURI(imageUri)
            1 -> checkImageUpload2.setImageURI(imageUri)
            2 -> checkImageUpload3.setImageURI(imageUri)
            3 -> checkImageUpload4.setImageURI(imageUri)
            else -> {
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val file = File(getRealPathFromURI(uri))
        return file
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val filePath = cursor?.getString(columnIndex!!)
        cursor?.close()
        return filePath ?: ""
    }

    fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    fun getToken(): String? {

        val prefHelper = PreferencesHelper.customAddForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    fun addForum(captions : String) {
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

        val file = File(path)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val images = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val captionPostingan = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), captions)

        getToken()?.let { token ->
            val call = apiInterface.postPostinganForum(token, images, captionPostingan)
            call.enqueue(object : Callback<CreateForumResponse> {
                override fun onResponse(
                    call: Call<CreateForumResponse>,
                    response: Response<CreateForumResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Add New Postingan Forum Success", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CreateForumResponse>, t: Throwable) {
                    // Handle kegagalan dengan sesuai
                }
            })
        }
    }




}