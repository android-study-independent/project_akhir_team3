package com.example.finalproject_chilicare.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}