package com.example.finalproject_chilicare.data.api

import android.widget.EditText
import com.example.finalproject_chilicare.data.response.LoginResponse
import com.example.finalproject_chilicare.data.response.UserRequest
import com.example.finalproject_chilicare.data.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Routes {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("fullName") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): retrofit2.Call<UserResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): retrofit2.Call<LoginResponse>

}