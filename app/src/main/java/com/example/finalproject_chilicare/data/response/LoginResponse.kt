package com.example.finalproject_chilicare.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    @Expose
    var message: String?,
    @SerializedName("data")
    @Expose
    var data: TokenData?
)

data class TokenData(
    @SerializedName("token")
    @Expose
    var token: String?
)