package com.example.finalproject_chilicare.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {

    fun getRetroClientInstance(url: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        // create retrofit
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


}