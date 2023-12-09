package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class CreateForumResponse(
    @SerializedName("error")
    val error: Boolean,

    @SerializedName("forum")
    val forum: AddNewForumResponse,

    @SerializedName("message")
    val message: String
)