package com.example.finalproject_chilicare.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkWeather {

    private var retrofit: Retrofit?=null

    var BASE_URL = "http://195.35.32.179:8003/"

    var BASE_URL2 = "https://api.openweathermap.org/data/2.5/"

    fun getApiInterface(): ApiInterface?{

        if (retrofit ==null){

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        return retrofit?.create(ApiInterface::class.java)

    }

    fun getCityApiInterface(): ApiInterface?{

        if (retrofit ==null){

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }

        return retrofit?.create(ApiInterface::class.java)


    }
}