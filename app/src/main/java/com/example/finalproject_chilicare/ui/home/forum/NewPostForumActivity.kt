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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.models.CreateForumResponse
import com.example.finalproject_chilicare.databinding.ActivityNewPostForumBinding
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.ui.home.HomeFragment
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
import java.io.FileOutputStream
import java.io.InputStream

class NewPostForumActivity : AppCompatActivity() {


    private val baseUrl = "http://195.35.32.179:8003/"
    lateinit var prefHelper: SharedPreferences

    lateinit var bindingPostForum: ActivityNewPostForumBinding
    lateinit var uploadImage: ImageView
    lateinit var checkImageUpload1: ImageView
    lateinit var checkImageUpload2: ImageView
    lateinit var checkImageUpload3: ImageView
    lateinit var checkImageUpload4: ImageView
    lateinit var inputPostingan: EditText
    lateinit var btnUploadPostingan: Button
    private var imagePathUri: Uri? = null
    private val selectedImageUris = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_post_forum)

        bindingPostForum = DataBindingUtil.setContentView(
            this@NewPostForumActivity,
            R.layout.activity_new_post_forum
        )

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
            addPostForum(inputPostingan.text.toString())
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
            addPostForum(
                bindingPostForum.etTextInputUpload.text.toString()
            )
            bindingPostForum.etTextInputUpload.text = null
            startActivity(Intent(this@NewPostForumActivity, HomeActivity::class.java))
        }
    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                Log.d("get_image", ":uri = ${it}")
                imagePathUri = it
                uploadImage.setImageURI(imagePathUri)

            }
        }

    private fun selectImage() {
        getContent.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    fun getToken(): String {

        val prefHelper = PreferencesHelper.customAddForum(this)
        return prefHelper.getString(PreferencesHelper.KEY_TOKEN, "").orEmpty()
    }

    private fun addPostForum(txtcaptions: String) {

        val apiInterface =
            Network().getRetroClientInstance(getToken()).create(ApiInterface::class.java)

        val file = uriToFile(imageUri = imagePathUri!!, this)
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val image: MultipartBody.Part =
            MultipartBody.Part.createFormData("images", file.name, requestFile)

        val captions: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), txtcaptions)

        getToken()?.let { token ->
            val call = apiInterface.postPostinganForum(image, captions)
            call.enqueue(object : Callback<CreateForumResponse?> {
                override fun onResponse(
                    call: Call<CreateForumResponse?>,
                    response: Response<CreateForumResponse?>
                ) {
                    if (response.isSuccessful) {

                        Toast.makeText(applicationContext, "Post Berhasil", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@NewPostForumActivity, ForumActivity::class.java)
                        startActivity(intent)

                    }
                }

                override fun onFailure(call: Call<CreateForumResponse?>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(
            buffer,
            0,
            length
        )
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("profil_user", ".jpg", storageDir)
    }


}