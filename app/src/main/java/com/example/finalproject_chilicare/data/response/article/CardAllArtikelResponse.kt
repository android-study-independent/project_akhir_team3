package com.example.finalproject_chilicare.data.response.article

import com.google.gson.annotations.SerializedName

data class CardAllArtikelResponse(

    @SerializedName("status")
    val status: String?,

    @SerializedName("message")
    val message: String?,

    val data: List<CardArtikelResponse>

)
