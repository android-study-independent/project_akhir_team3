package com.example.finalproject_chilicare.ui.home.forum

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.PreferencesHelper.IMAGE_REQUEST
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.models.AddNewForumResponse
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.CreateForumResponse
import com.example.finalproject_chilicare.databinding.ActivityNewPostForumBinding
import okhttp3.MediaType
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

    lateinit var bindingPostForum : ActivityNewPostForumBinding
    lateinit var uploadImage : ImageView
    lateinit var checkImageUpload1 : ImageView
    lateinit var checkImageUpload2 : ImageView
    lateinit var checkImageUpload3 : ImageView
    lateinit var checkImageUpload4 : ImageView
    lateinit var inputPostingan : EditText
    lateinit var btnUploadPostingan : Button
    private var path: String = ""
    private val selectedImageUris = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_post_forum)

       bindingPostForum = DataBindingUtil.setContentView(this@NewPostForumActivity, R.layout.activity_new_post_forum)

        prefHelper = PreferencesHelper.customAddForum(this)

        // GO PAGES FORUM
        val ivBack = findViewById<ImageView>(R.id.ivBack)

        ivBack.setOnClickListener {
            Intent(this, ForumActivity::class.java).also {
                startActivity(it)
            }
        }

        uploadImage = findViewById(R.id.ivMediaFoto)
        checkImageUpload1 = findViewById(R.id.checkImageUpload1)
        checkImageUpload2 = findViewById(R.id.checkImageUpload2)
        checkImageUpload3 = findViewById(R.id.checkImageUpload3)
        checkImageUpload4 = findViewById(R.id.checkImageUpload4)
        inputPostingan = findViewById(R.id.etTextInputUpload)
        btnUploadPostingan = findViewById(R.id.btPostingForumUpload)


        // Call checkPermissions before attempting to select an image
        checkPermissions()

        btnUploadPostingan.setOnClickListener {
            addCustomer(inputPostingan.text.toString())
        }


        clickListeners()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@NewPostForumActivity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private fun clickListeners() {
        bindingPostForum.ivMediaFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectImage()
            } else {
                ActivityCompat.requestPermissions(
                    this@NewPostForumActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        }

        bindingPostForum.btPostingForumUpload.setOnClickListener {
            addCustomer(
                bindingPostForum.etTextInputUpload.text.toString()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val realPath: String? = getRealPathFromUri(uri)
                if (!realPath.isNullOrBlank()) {
                    path = realPath
                    // Display the selected image using Glide or other image loading library
                    loadImageFromPath(path)
                } else {
                    Toast.makeText(
                        this@NewPostForumActivity,
                        "Failed to get real path",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {
                Toast.makeText(
                    this@NewPostForumActivity,
                    "Data or Uri is null",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadImageFromPath(path: String) {
        // Log the file path and existence
        Log.d("FILE_PATH", "Path: $path, File exists: ${File(path).exists()}")

        // Load the image using BitmapFactory
        try {
            val bitmap: Bitmap = BitmapFactory.decodeFile(path)
            // Set the bitmap to the ImageView
            bindingPostForum.checkImageUpload1.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this@NewPostForumActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRealPathFromUri(uri: Uri): String? {
        // Log the URI information
        Log.d("URI_INFO", "URI: $uri, Scheme: ${uri.scheme}")

        var realPath: String? = null
        try {
            // Handle different URI schemes
            when {
                ContentResolver.SCHEME_CONTENT == uri.scheme -> {
                    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val documentId = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DOCUMENT_ID))
                            realPath = "content://media/external/images/media/$documentId"
                            Log.d("REAL_PATH", "Real path from content: $realPath")
                        }
                    }
                }
                ContentResolver.SCHEME_FILE == uri.scheme -> {
                    realPath = uri.path
                    Log.d("REAL_PATH", "Real path from file: $realPath")
                }
                else -> {
                    Log.e("REAL_PATH", "Unsupported scheme: ${uri.scheme}")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REAL_PATH", "Error getting real path: ${e.message}")
        }
        return realPath
    }





    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val realPath: String? = getRealPathFromUri(it)
            if (!realPath.isNullOrBlank()) {
                path = realPath
                val bitmap: Bitmap? = BitmapFactory.decodeFile(path)
                if (bitmap != null) {
                    bindingPostForum.checkImageUpload1.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(this@NewPostForumActivity, "Failed to decode bitmap", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@NewPostForumActivity, "Failed to get real path", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectImage() {
        getContent.launch("image/*")
    }


    fun getToken(): String? {

        val prefHelper = PreferencesHelper.customAddForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    private fun addCustomer(name: String) {
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


        val file = File(path!!)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", file.name, requestFile)

        val cusName: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)

        getToken()?.let { token ->
            val call = apiInterface.postPostinganForum(token, body, cusName)
            call.enqueue(object : Callback<CreateForumResponse?> {
                override fun onResponse(
                    call: Call<CreateForumResponse?>,
                    response: Response<CreateForumResponse?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.toString() == "200") {
                            Toast.makeText(applicationContext, "Customer Added", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(applicationContext, "Not Added", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                override fun onFailure(call: Call<CreateForumResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}