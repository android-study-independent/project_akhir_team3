package com.example.finalproject_chilicare.data.models

import com.google.gson.annotations.SerializedName

data class IsiComentarItem(
    @SerializedName("id_komentar")
    val idKomentar: Int,

    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("komentar")
    val komentar: String,

    @SerializedName("name")
    val name: String
)