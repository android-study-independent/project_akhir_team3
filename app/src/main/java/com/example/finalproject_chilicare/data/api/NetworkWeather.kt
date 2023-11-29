package com.example.finalproject_chilicare.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkWeather {

    private var retrofit: Retrofit? = null

    var BASE_URL = "http://195.35.32.179:8003/"

    var BASE_URL2 = "https://api.openweathermap.org/data/2.5/"

    fun getApiInterface(): ApiInterface? {

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()
        }

        return retrofit?.create(ApiInterface::class.java)

    }

    fun getCityApiInterface(): ApiInterface? {

        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()

        }

        return retrofit?.create(ApiInterface::class.java)


    }

    private fun createOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}