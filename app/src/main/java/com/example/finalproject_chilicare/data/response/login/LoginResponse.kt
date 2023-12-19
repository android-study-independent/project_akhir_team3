package com.example.finalproject_chilicare.data.response.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse(

    @SerializedName("message")
    @Expose
    var message: String?,

    @SerializedName("fullName")
    var fullname : String? = null,

    @SerializedName("email")
    var email :String? = null,

    @SerializedName("data")
    @Expose
    var data: TokenData?

)

class TokenData(

    @SerializedName("token")
    @Expose
    var token: String?

)