package com.example.finalproject_chilicare.data.api

import android.content.Context
import com.example.finalproject_chilicare.data.PreferencesHelper.KEY_TOKEN
import com.example.finalproject_chilicare.data.PreferencesHelper.KEY_TOKEN_FILE
import com.example.finalproject_chilicare.data.PreferencesHelper.get
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Inject

class Token @Inject constructor(@ApplicationContext context: Context) {

    private var tokenPrefs = context.getSharedPreferences(KEY_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken (token : String) {

        val editor = tokenPrefs.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()

    }

    fun getToken () : String? {

        return tokenPrefs.getString(KEY_TOKEN, null)

    }





}