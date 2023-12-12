package com.example.finalproject_chilicare.data.models

import com.example.finalproject_chilicare.ui.home.forum.ForumActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class AllForumResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val allForumItem: List<AllForumItem>

)