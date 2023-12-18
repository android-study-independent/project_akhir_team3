package com.example.finalproject_chilicare.data.api

import com.example.finalproject_chilicare.data.models.AllForumResponse
import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.data.models.CreateForumResponse
import com.example.finalproject_chilicare.data.models.DeleteForumResponse
import com.example.finalproject_chilicare.data.models.EditForumResponse
import com.example.finalproject_chilicare.data.models.LikeForumResponse
import com.example.finalproject_chilicare.data.models.PostKomentarDetailForumResponse
import com.example.finalproject_chilicare.data.models.UnlikeResponse
import com.example.finalproject_chilicare.data.response.article.CardAllArtikelResponse
import com.example.finalproject_chilicare.data.response.login.LoginRequest
import com.example.finalproject_chilicare.data.response.login.LoginResponse
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse

import com.example.finalproject_chilicare.data.response.lms.CardAllModulResponse
import com.example.finalproject_chilicare.data.response.lms.ModulStatusRespn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @POST("auth/register")
    fun createUser(@Body req: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun userLogin(@Body req: LoginRequest): Call<LoginResponse>

    @POST("auth/login")
    fun getFullnameLogin (): Call<LoginResponse>

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
    fun getListMateri() : Call<CardLmsResponse>

    @GET("lms/all_modul")
    suspend fun getAllLms():  CardAllModulResponse
    @GET("lms/modul/{id}")
    fun getMateribyId(@Path ("id") id: String): Call<CardAllModulResponse>

    @GET("lms/all_modul")
    suspend fun getMateriLms() : Call <CardAllModulResponse>


    @GET("forum/semua_postingan")
    fun getAllForum() : Call<AllForumResponse>

    @GET("forum/postingan/{id}")
    fun getKomentar(
        @Path("id") id: String?
    ) : Call<ForumResponse>

    @Multipart
    @POST("forum/buat_postingan")
    fun postPostinganForum(
        @Part images: MultipartBody.Part,
        @Part ("captions") Captions: RequestBody,
    ) : Call<CreateForumResponse>

    @DELETE("forum/hapus_postingan/{id}")
    fun deletePostingan(
        @Path("id") id: String,
    ): Call<DeleteForumResponse>

    @Multipart
    @PUT("forum/edit_captions/{id}")
    fun updateCaptions(
        @Path("id") id: String,
        @Part("captions") captions: RequestBody
    ): Call<EditForumResponse>


    @POST("forum/komentar/{id}")
    fun postKomentar(
        @Path("id") id: String,
        @Body requestBody: RequestBody
    ): Call<PostKomentarDetailForumResponse>

    @PUT("forum/like/{id}")
    fun likePostingan(
        @Path("id") id: String,
    ): Call<LikeForumResponse>

    @PUT("forum/unlike/{id}")
    fun unlikePostingan(
        @Path("id") id: String,
    ): Call<UnlikeResponse>
}



