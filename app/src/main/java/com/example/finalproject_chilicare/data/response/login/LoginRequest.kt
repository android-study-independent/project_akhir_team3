package com.example.finalproject_chilicare.data.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email") val requestEmail: String,
    @SerializedName("password") val requestPassword: String
)