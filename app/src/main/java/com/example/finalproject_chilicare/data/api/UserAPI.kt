package com.example.finalproject_chilicare.data.api

import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("register")
    fun createUser(@Body req: RegisterRequest) : Call<RegisterResponse>
}