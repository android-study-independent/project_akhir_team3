package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddNewForumResponse(
    @SerializedName ("captions")
    val captions: String,

    @SerializedName ("createdAt")
    val createdAt: String,

    @SerializedName ("id_user")
    val idUser: Int,

    @SerializedName ("image")
    val image: List<String>,

    @SerializedName ("name")
    val name: String
)