package com.example.finalproject_chilicare.data.response.lms

import com.google.gson.annotations.SerializedName

data class CardAllLmsResponse(
    @SerializedName("status")
    val status : String?,
    @SerializedName("message")
    val message : String?,

    val data : List<CardLmsResponse>


)
