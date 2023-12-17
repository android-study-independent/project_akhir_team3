package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class LikeForumResponse(

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("like")
    val like: LikeItem,
)