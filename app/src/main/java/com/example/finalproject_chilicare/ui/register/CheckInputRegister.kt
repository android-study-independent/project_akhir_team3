package com.example.finalproject_chilicare.ui.register

import android.os.AsyncTask
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class CheckInputRegister (private val email: String, private val callback: (Boolean) -> Unit) :
    AsyncTask<Void, Void, Boolean>(){
    override fun doInBackground(vararg params: Void?): Boolean {
        val client = OkHttpClient()

        val requestBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .build()

        val request = Request.Builder()
            .url("http://195.35.32.179:8003/auth/")
            .post(requestBody)
            .build()

        return try {
            val response = client.newCall(request).execute()
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override fun onPostExecute(result: Boolean) {
        callback.invoke(result)
    }
}