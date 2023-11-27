package com.example.finalproject_chilicare.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkWeather {

    private var retrofit: Retrofit?=null

    var BASE_URL = "http://195.35.32.179:8003/"

    fun getApiInterface(): ApiInterface?{

        if (retrofit ==null){

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()


        }

        return retrofit?.create(ApiInterface::class.java)


    }
}