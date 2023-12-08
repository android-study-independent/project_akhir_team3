package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class AllForumItem(
    val captions: String,
    val createdAt: String,
    val forumId: Int,

    @SerializedName ("id_user")
    val idUser: Int,
    val image: List<String>,
    val jumlahKomentar: Int,
    val jumlahLike: Int,

    @SerializedName("name_user")
    val nameUser: String
)