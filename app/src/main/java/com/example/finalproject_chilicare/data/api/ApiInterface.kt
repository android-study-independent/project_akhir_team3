package com.example.finalproject_chilicare.data.api

import com.example.finalproject_chilicare.data.models.AddPostForumRequest
import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.data.models.AddNewForumResponse
import com.example.finalproject_chilicare.data.response.article.CardAllArtikelResponse
import com.example.finalproject_chilicare.data.response.login.LoginRequest
import com.example.finalproject_chilicare.data.response.login.LoginResponse
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.example.finalproject_chilicare.data.response.lms.CardAllLmsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiInterface {

    @POST("auth/register")
    fun createUser(@Body req: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun userLogin(@Body req: LoginRequest): Call<LoginResponse>

    @GET("weather")
    fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<CurrentWeather>


    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<CurrentWeather>


    @GET("weather")
    fun getCityWeatherData(
        @Query("q") q: String,
        @Query("APPID") appid: String
    ): Call<CurrentWeather>

    @GET("artikel/all_artikel")
    suspend fun getAllArtikel(): CardAllArtikelResponse

    @GET("lms/all_modul")
    suspend fun getAllLms() : CardAllLmsResponse

   // @Headers("x-api-key")
    @GET("forum/semua_postingan")
    fun getAllForum(@Header("x-api-key") apiKey : String) : Call<AllForumResponse>

//    @Multipart
//    @POST("forum/buat_postingan")
//    fun postPostinganForum(
//        @Header("x-api-key") apiKey : String,
//        @Part ("captions") Captions : RequestBody,
//        @Part images: MultipartBody.Part,
//        @Body req: AddPostForumRequest
//    ) : Call<AddNewForumResponse>



}