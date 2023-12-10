package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddNewForumResponse(
    @SerializedName ("captions")
    @Expose
    val captions: String,

    @SerializedName ("createdAt")
    @Expose
    val createdAt: String,

    @SerializedName ("id_user")
    @Expose
    val idUser: Int,

    @SerializedName ("image")
    @Expose
    val image: List<String>,

    @SerializedName ("name")
    @Expose
    val name: String
)