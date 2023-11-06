package com.example.finalproject_chilicare.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse (val status: String, val message: String)



//    @SerializedName("data")
//    @Expose
//    val data: user? = null
//
//    class user {
//        @SerializedName("email")
//        @Expose
//        var email: String? = ""
//
//        @SerializedName("password")
//        @Expose
//        var password: String? = ""
//
//        @SerializedName("token")
//        @Expose
//        var token: String? = ""
//    }


