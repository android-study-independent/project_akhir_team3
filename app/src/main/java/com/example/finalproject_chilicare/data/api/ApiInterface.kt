package com.example.finalproject_chilicare.data.api

import com.example.finalproject_chilicare.data.response.LoginRequest
import com.example.finalproject_chilicare.data.response.LoginResponse
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("register")
    fun createUser(@Body req: RegisterRequest) : Call<RegisterResponse>

    @POST("login")
    fun userLogin(@Body req: LoginRequest) : Call<LoginResponse>

    @GET("weather?")
    fun getCurrentWeather(
        @Query("api_key") apiKey : String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    )

    @GET("weather?")
    fun getWeatherByCity(
        @Query("q") city : String,
        @Query("lon") lon: String
    )
}