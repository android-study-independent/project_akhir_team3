package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class EditForumResponse(

    @SerializedName("Forum")
    val forum: ForumEditItem,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String
)