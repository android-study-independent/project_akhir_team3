package com.example.finalproject_chilicare.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

//class HeaderInterceptor @Inject constructor(): Interceptor {
//    @Inject
//    lateinit var tokenManager : Token
//
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val req = chain.request().newBuilder()
//
//        val token = tokenManager.getToken()
//        req.addHeader("x-api-key", "$token")
//
//        return chain.proceed(req.build())
//    }
//
//
//}