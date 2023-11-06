package com.example.finalproject_chilicare.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import android.util.Base64
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

    private val AUTH = "Basic "+ Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)
    private const val BASE_URL = "https://acaf-103-189-201-221.ngrok-free.app/auth/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Routes by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Routes::class.java)
    }



//    private fun builder(context: Context): Retrofit {
//
//        // Create the Collector
//        val chuckerCollector = ChuckerCollector(
//            context = context,
//            showNotification = true,
//            retentionPeriod = RetentionManager.Period.ONE_HOUR
//        )
//
//        // Create the Interceptor
//        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
//            .collector(chuckerCollector)
//            .maxContentLength(250_000L)
//            .alwaysReadResponseBody(true)
//            .createShortcut(true)
//            .build()
//
//        // create http logging incerecptor
//        val loggingInterecptor = HttpLoggingInterceptor()
//        loggingInterecptor.level = HttpLoggingInterceptor.Level.BODY
//
//
//        // create okhttpclient
////        val client = OkHttpClient.Builder()
////            .connectTimeout(30, TimeUnit.SECONDS)
////            .writeTimeout(30, TimeUnit.SECONDS)
////            .readTimeout(30, TimeUnit.SECONDS)
////            .addInterceptor(HeaderInterceptor())
////            .addInterceptor(loggingInterecptor)
////            .addInterceptor(chuckerInterceptor)
////            .build()
//
//        // mengubah data yang tadinya json menjadi sebuah object
//        val gson = GsonBuilder().create()
//
//        // create retrofit
//        return Retrofit.Builder()
//            .baseUrl("localhost:1945/auth/")
////            .client(client)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

//    fun getService(context: Context): Routes = builder(context).create(Routes::class.java)
}