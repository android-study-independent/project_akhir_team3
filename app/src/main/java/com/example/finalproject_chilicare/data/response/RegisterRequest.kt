package com.example.finalproject_chilicare.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterRequest {

    @SerializedName("fullName")
    @Expose
    var fullname: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null


}