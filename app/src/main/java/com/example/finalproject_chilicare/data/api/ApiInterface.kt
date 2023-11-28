package com.example.finalproject_chilicare.data.api

import com.example.finalproject_chilicare.data.models.CurrentWeather
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
    fun createUser(@Body req: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun userLogin(@Body req: LoginRequest): Call<LoginResponse>

    @GET("weather")
    fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<CurrentWeather>


    @GET("weather")
    fun  getCurrentWeatherData(
        @Query("api_key") apiKey: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ) : Call<CurrentWeather>

    @GET("weather")
    fun  getCityWeatherData(
        @Query("q") q:String,
        @Query("APPID") appid:String
    ) : Call<CurrentWeather>


}