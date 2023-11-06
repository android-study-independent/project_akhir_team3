package com.example.finalproject_chilicare.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null
}