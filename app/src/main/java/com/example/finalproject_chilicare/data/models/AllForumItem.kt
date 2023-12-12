package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class AllForumItem(
    @SerializedName ("captions")
    val captions: String,

    @SerializedName ("createdAt")
    val createdAt: String,

    @SerializedName ("forumId")
    val forumId: Int,

    @SerializedName ("id_user")
    val idUser: Int,

    @SerializedName ("image")
    val image: List<String>,

    @SerializedName ("jumlahKomentar")
    val jumlahKomentar: Int,

    @SerializedName ("jumlahLike")
    val jumlahLike: Int,

    @SerializedName("name_user")
    val nameUser: String
)


